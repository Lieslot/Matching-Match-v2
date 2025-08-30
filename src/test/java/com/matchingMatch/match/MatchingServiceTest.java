package com.matchingMatch.match;

import static org.assertj.core.data.Offset.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.matchingMatch.TestDataBuilder;
import com.matchingMatch.match.domain.MannerRate;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.StadiumAdapter;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.MatchRequestRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.exception.MatchAlreadyConfirmedException;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
import com.matchingMatch.match.implement.MannerRater;
import com.matchingMatch.match.implement.MatchAdapter;
import com.matchingMatch.match.implement.MatchRequestAdapter;
import com.matchingMatch.match.implement.MatchTeamValidator;
import com.matchingMatch.match.implement.TeamAdapter;
import com.matchingMatch.match.service.MatchingService;
import com.matchingMatch.team.domain.Team;
import com.matchingMatch.user.domain.UserDetail;
import com.matchingMatch.user.domain.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@DataJpaTest
@Import({MatchingService.class, MatchAdapter.class, MatchRequestAdapter.class, TeamAdapter.class
	, MatchTeamValidator.class, MannerRater.class, StadiumAdapter.class})
public class MatchingServiceTest {

	@Autowired
	private MatchingService matchService;

	@Autowired
	private MatchAdapter matchAdapter;

	@Autowired
	private StadiumAdapter stadiumAdapter;

	@Autowired
	private MatchRequestAdapter matchRequestAdapter;

	@Autowired
	private MatchTeamValidator matchTeamValidator;

	@Autowired
	private MannerRater mannerRater;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private TeamAdapter teamAdapter;

	TestDataBuilder testDataBuilder = new TestDataBuilder();

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TestEntityManager testEntityManager;
	@Autowired
	private MatchRequestRepository matchRequestRepository;

	private UserDetail hostUser;
	private UserDetail guestUser;
	private UserDetail guestUser2;
	private Team hostTeam;
	private Team guestTeam;
	private Team guestTeam2;
	private Long hostId;
	private Long guestId;
	private Long guestId2;
	private MatchEntity match;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@BeforeEach
	void setUp() {

		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		transactionTemplate.execute(status -> {
			hostUser = testDataBuilder.createNotPersistedUser("host");
			guestUser = testDataBuilder.createNotPersistedUser("guest");
			guestUser2 = testDataBuilder.createNotPersistedUser("guest2");
			testEntityManager.persistAndFlush(hostUser);
			testEntityManager.persistAndFlush(guestUser);
			testEntityManager.persistAndFlush(guestUser2);

			hostTeam = testDataBuilder.createNotPersistedTeam(hostUser.getId(), "default");
			guestTeam = testDataBuilder.createNotPersistedTeam(guestUser.getId(), "default1");
			guestTeam2 = testDataBuilder.createNotPersistedTeam(guestUser2.getId(), "default2");
			hostId = teamAdapter.save(hostTeam);
			guestId = teamAdapter.save(guestTeam);
			guestId2 = teamAdapter.save(guestTeam2);

			match = testDataBuilder.createNotPersistedMatchEntity(hostId);
			testEntityManager.persistAndFlush(match);
			return null;
		});

		match = testEntityManager.merge(match); // merge into test persistence context

	}

	@AfterEach
	void tearDown() {
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

		transactionTemplate.execute(status -> {
			testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE user_detail").executeUpdate();
			testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE match_request").executeUpdate();
			testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE match").executeUpdate();
			testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE team").executeUpdate();
			return null;

		});


	}

	@Test
	void match_request_send_success() {
		// given

		// when
		matchService.sendMatchRequest(match.getId(), guestId);

		// then
		Assertions.assertThat(matchRequestRepository.count()).isEqualTo(1);
	}

	@Test
	void match_request_send_failed_when_match_has_already_confirmed() {
		// given
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.sendMatchRequest(match.getId(), guestId)
		);

		// then
		Assertions.assertThat(result).isInstanceOf(MatchAlreadyConfirmedException.class);
	}

	@Test
	void match_request_send_failed_when_team_not_exists() {
		// given

		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.sendMatchRequest(match.getId(), 0L)
		);

		// then
		Assertions.assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}


	@Test
	void match_request_cancel_success() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		MatchRequestEntity matchRequest = matchRequestRepository.findAll().get(0);

		// when
		matchService.cancelMatchRequest(matchRequest.getId(), guestUser.getId());

		// then
		Assertions.assertThat(matchRequestRepository.count()).isEqualTo(0);
	}

	// TODO 추후에 동시성 문제로 테스트 작성
	@Test
	void match_request_cancel_failed_when_match_has_already_confirmed() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		MatchRequestEntity matchRequest = matchRequestRepository.findAll().get(0);
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.cancelMatchRequest(matchRequest.getId(), hostUser.getId())
		);

		// then
		Assertions.assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}


	@Test
	void match_request_confirm_success() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		MatchRequestEntity matchRequest = matchRequestRepository.findAll().get(0);

		// when
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		// then
		Assertions.assertThat(matchRequestRepository.count()).isEqualTo(0);
		Match match = matchAdapter.getMatchBy(this.match.getId());
		Assertions.assertThat(match.isConfirmed()).isTrue();
	}

	void match_request_confirm_failed_when_match_has_already_confirmed() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		MatchRequestEntity matchRequest = matchRequestRepository.findAll().get(0);
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId)
		);

		// then
		Assertions.assertThat(result).isInstanceOf(MatchAlreadyConfirmedException.class);
	}

	@Test
	void match_request_confirm_failed_when_user_is_not_host() {
		// given

		matchService.sendMatchRequest(match.getId(), guestId);
		MatchRequestEntity matchRequest = matchRequestRepository.findAll().get(0);
		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.confirmMatchRequest(match.getId(), guestUser.getId(), guestId)
		);

		// then
		Assertions.assertThat(result).isInstanceOf(UnauthorizedAccessException.class);
	}


	@Test
	void match_request_reject_success() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		MatchRequestEntity matchRequest = matchRequestRepository.findAll().get(0);

		// when
		matchService.refuseMatchRequest(matchRequest.getId(), hostUser.getId());

		// then
		Assertions.assertThat(matchRequestRepository.count()).isEqualTo(0);
	}


	// TODO 추후에 동시성 테스트
	@Test
	void match_request_reject_failed_when_match_has_already_confirmed() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		MatchRequestEntity matchRequest = matchRequestRepository.findAll().get(0);
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.refuseMatchRequest(matchRequest.getId(), hostUser.getId())
		);

		// then
		Assertions.assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void match_request_reject_failed_when_user_is_not_host() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		MatchRequestEntity matchRequest = matchRequestRepository.findAll().get(0);

		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.refuseMatchRequest(matchRequest.getId(), guestUser.getId())
		);

		// then
		Assertions.assertThat(result).isInstanceOf(UnauthorizedAccessException.class);
	}

	@Test
	void manner_rate_success_host() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);
		match.setEndTime(LocalDateTime.now().minusHours(1L));

		// when
		matchService.rateMannerPoint(match.getId(), new MannerRate(hostUser.getId(), 2L));

		// then
		Match match = matchAdapter.getMatchBy(this.match.getId());
		Team team = teamAdapter.getTeamBy(hostId);
		Assertions.assertThat(team.getMannerPoint())
			.isCloseTo(BigDecimal.valueOf(2L), offset(BigDecimal.valueOf(0.1)));
		Assertions.assertThat(match.getIsHostRate()).isTrue();
		Assertions.assertThat(match.getIsParticipantRate()).isFalse();
	}

	@Test
	void manner_rate_success_participant() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		match.setEndTime(LocalDateTime.now().minusHours(1L));
		matchAdapter.save(match);

		// when
		matchService.rateMannerPoint(match.getId(), new MannerRate(guestUser.getId(), 2L));

		// then
		Match match = matchAdapter.getMatchBy(this.match.getId());
		Team team = teamAdapter.getTeamBy(guestId);
		Assertions.assertThat(team.getMannerPoint())
			.isCloseTo(BigDecimal.valueOf(2L), offset(BigDecimal.valueOf(0.1)));
		Assertions.assertThat(match.getIsHostRate()).isFalse();
		Assertions.assertThat(match.getIsParticipantRate()).isTrue();
	}

	@Test
	void manner_rate_failed_when_already_rated() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		match.setEndTime(LocalDateTime.now().minusHours(1L));
		matchAdapter.save(match);

		matchService.rateMannerPoint(match.getId(), new MannerRate(hostUser.getId(), 2L));

		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.rateMannerPoint(match.getId(), new MannerRate(hostUser.getId(), 2L))
		);

		// then
		Assertions.assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void manner_rate_failed_when_match_not_end() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		match.setEndTime(LocalDateTime.now().plusHours(1L));
		matchAdapter.save(match);

		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.rateMannerPoint(match.getId(), new MannerRate(hostUser.getId(), 2L))
		);

		// then
		Assertions.assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void match_confirm_cancel_success() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		match.setConfirmedTime(LocalDateTime.now().minusMinutes(10L));
		matchAdapter.save(match);

		// when
		matchService.cancelConfirmedMatch(match.getId(), hostUser.getId());

		// then
		Match match = matchAdapter.getMatchBy(this.match.getId());
		Assertions.assertThat(match.isConfirmed()).isFalse();
	}

	@Test
	void match_confirm_cancel_failed_when_cancel_deadline_is_not_over() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		match.setConfirmedTime(LocalDateTime.now().minusMinutes(9L));
		matchAdapter.save(match);
		TestTransaction.isActive();

		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.cancelConfirmedMatch(match.getId(), hostUser.getId())
		);

		// then
		System.out.println(result.getMessage());
		Assertions.assertThat(result).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void match_confirm_cancel_failed_when_user_is_not_host_or_participant() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		match.setConfirmedTime(LocalDateTime.now().minusMinutes(10L));
		matchAdapter.save(match);

		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.cancelConfirmedMatch(match.getId(), 0L)
		);

		// then
		Assertions.assertThat(result).isInstanceOf(UnauthorizedAccessException.class);
	}

	@Test
	void match_confirm_success() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);

		// when
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		// then
		Match match = matchAdapter.getMatchBy(this.match.getId());
		Assertions.assertThat(match.isConfirmed()).isTrue();
	}

	@Test
	void match_confirm_failed_when_match_has_already_confirmed() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);
		matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId);

		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId)
		);

		// then
		Assertions.assertThat(result).isInstanceOf(MatchAlreadyConfirmedException.class);
	}

	@Test
	void match_confirm_failed_when_user_is_not_host() {
		// given
		matchService.sendMatchRequest(match.getId(), guestId);

		// when
		Throwable result = Assertions.catchThrowable(() ->
			matchService.confirmMatchRequest(match.getId(), guestUser.getId(), guestId)
		);

		// then
		Assertions.assertThat(result).isInstanceOf(UnauthorizedAccessException.class);
	}
    // Bad Case
	@Test
	void match_request_after_match_confirm_and_send_match_request_concurrently() {
		// given
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		matchService.sendMatchRequest(match.getId(), guestId2);

		// when
		Future<?> future2 = executorService.submit(() -> {
			matchService.sendMatchRequest(match.getId(), guestId);
		});
		Future<?> future1 = executorService.submit(() -> {
			matchService.confirmMatchRequest(match.getId(), hostUser.getId(), guestId2);
		});

		try {
			future1.get();
			future2.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		// then
		int requestCount = matchService.getRequestThumbnails(hostUser.getId()).size();
		Assertions.assertThat(requestCount).isEqualTo(1);
		Assertions.assertThat(matchRequestRepository.count()).isEqualTo(0);

		executorService.shutdown();
	}

}
