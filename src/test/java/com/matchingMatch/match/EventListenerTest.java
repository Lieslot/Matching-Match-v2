package com.matchingMatch.match;

import com.matchingMatch.TestDataBuilder;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import com.matchingMatch.match.implement.MatchAdapter;
import com.matchingMatch.match.implement.TeamAdapter;
import com.matchingMatch.match.service.MatchPostService;
import com.matchingMatch.team.domain.entity.LeaderRequestEntity;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.team.domain.entity.TeamEntity;
import com.matchingMatch.team.service.TeamService;
import com.matchingMatch.user.domain.UserDetail;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@AutoConfigureTestEntityManager
public class EventListenerTest {

    @Autowired
    TestEntityManager testEntityManager;

    TestDataBuilder testDataBuilder = new TestDataBuilder();

    @Autowired
	TeamAdapter teamAdapter;
    @Autowired
	MatchAdapter matchAdapter;
    @Autowired
    TeamService teamService;
    @Autowired
    PlatformTransactionManager platformTransactionManager;
    @Autowired
    private MatchPostService matchPostService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public Executor executor() {
            return new SyncTaskExecutor();
        }
    }

    @AfterEach
    void tearDown() {
        testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE user_detail").executeUpdate();
        testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE leader_request").executeUpdate();
        testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE match_request").executeUpdate();
        testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE match").executeUpdate();
        testEntityManager.getEntityManager().createNativeQuery("TRUNCATE TABLE team").executeUpdate();
    }

    @Transactional
    @Test
    void delete_all_connect_to_team() {

        // given
        AtomicReference<Long> teamId = new AtomicReference<>();
        AtomicReference<Long> matchId = new AtomicReference<>();
        AtomicReference<Long> matchRequestId = new AtomicReference<>();

        TransactionTemplate givenTransactionTemplate = new TransactionTemplate(platformTransactionManager,
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));

        givenTransactionTemplate.execute(status -> {
            UserDetail leader1 = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default1"));
            UserDetail leader2 = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default2"));

            testEntityManager.persist(testDataBuilder.createNotPersistedStadiumEntity());

            teamId.set(teamAdapter.save(testDataBuilder.createNotPersistedTeam(leader1.getId(), "default3")));
            Long targetTeamId = teamAdapter.save(testDataBuilder.createNotPersistedTeam(leader2.getId(), "default4"));

            Team host = testEntityManager.find(TeamEntity.class, teamId.get()).toDomain();
            MatchEntity match = testDataBuilder.createNotPersistedMatchEntity(host.getId());
            match.setParticipantId(targetTeamId);
            matchId.set(matchAdapter.save(match));

            MatchRequestEntity matchRequest = MatchRequestEntity.builder()
                    .sendTeamId(teamId.get())
                    .targetTeamId(targetTeamId)
                    .matchId(matchId.get())
                    .build();
            matchRequestId.set(testEntityManager.persistAndGetId(matchRequest, Long.class));

            testEntityManager.persist(LeaderRequestEntity.builder()
                    .teamId(teamId.get())
                    .sendUserId(leader1.getId())
                    .targetUserId(leader2.getId())
                    .build());

            return null;
        });

        // when
        TransactionTemplate whenTransactionTemplate = new TransactionTemplate(platformTransactionManager,
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));

        whenTransactionTemplate.execute(status -> {
            teamService.deleteTeam(teamId.get());
            return null;
        });

        // then
        Assertions.assertThat(testEntityManager.find(MatchRequestEntity.class, matchRequestId.get())).isNull();
        Assertions.assertThat(testEntityManager.find(MatchEntity.class, matchId.get())).isNull();
        Assertions.assertThat(testEntityManager.find(TeamEntity.class, teamId.get())).isNull();
        Assertions.assertThat(testEntityManager.find(LeaderRequestEntity.class, teamId.get())).isNull();
    }
    @Transactional
    @Test
    void delete_all_match() {

            // given
            AtomicReference<Long> matchId = new AtomicReference<>();
            AtomicReference<Long> matchRequestId = new AtomicReference<>();
            AtomicReference<Long> hostLeaderId = new AtomicReference<>();

            TransactionTemplate givenTransactionTemplate = new TransactionTemplate(platformTransactionManager,
                    new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));


            givenTransactionTemplate.execute(status -> {
                UserDetail leader1 = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default1"));
                UserDetail leader2 = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default2"));

                hostLeaderId.set(leader1.getId());

                testEntityManager.persist(testDataBuilder.createNotPersistedStadiumEntity());

                Long teamId = teamAdapter.save(testDataBuilder.createNotPersistedTeam(leader1.getId(), "default1"));
                Long targetTeamId = teamAdapter.save(testDataBuilder.createNotPersistedTeam(leader2.getId(), "default2"));

                Team host = testEntityManager.find(TeamEntity.class, teamId).toDomain();
                MatchEntity match = testDataBuilder.createNotPersistedMatchEntity(host.getId());
                match.setParticipantId(targetTeamId);
                matchId.set(matchAdapter.save(match));

                MatchRequestEntity matchRequest = MatchRequestEntity.builder()
                        .sendTeamId(teamId)
                        .targetTeamId(targetTeamId)
                        .matchId(matchId.get())
                        .build();
                matchRequestId.set(testEntityManager.persistAndGetId(matchRequest, Long.class));

                return null;
            });

            // when
            TransactionTemplate whenTransactionTemplate = new TransactionTemplate(platformTransactionManager,
                    new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));

            whenTransactionTemplate.execute(status -> {
                matchPostService.deleteMatchPost(matchId.get(), hostLeaderId.get());
                return null;
            });


            // then
            assertAll(
                    () -> Assertions.assertThat(testEntityManager.find(MatchRequestEntity.class, matchRequestId.get())).isNull(),
                    () -> Assertions.assertThat(testEntityManager.find(MatchEntity.class, matchId.get())).isNull()
            );
    }


}
