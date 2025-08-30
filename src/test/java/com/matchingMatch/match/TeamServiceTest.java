package com.matchingMatch.match;

import com.matchingMatch.TestDataBuilder;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
import com.matchingMatch.match.implement.TeamAdapter;
import com.matchingMatch.team.domain.entity.LeaderRequestEntity;
import com.matchingMatch.team.domain.Team;
import com.matchingMatch.team.domain.repository.LeaderRequestRepository;
import com.matchingMatch.team.service.TeamService;
import com.matchingMatch.user.domain.UserDetail;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DataJpaTest
@Import({TeamService.class, TeamAdapter.class})
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @SpyBean
    private TeamAdapter teamAdapter;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private LeaderRequestRepository leaderRequestRepository;

    TestDataBuilder testDataBuilder = new TestDataBuilder();



    // 팀 리더 양도 요청 성공 테스트

    @Transactional
    @Test
    void leaderTransferRequestSuccess() {
        // given
        UserDetail leader = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default1"));
        UserDetail leader2 = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default2"));

        Long teamId = teamAdapter.save(testDataBuilder.createNotPersistedTeam(leader.getId(), "default"));
        // when
        teamService.createLeaderRequest(teamId, leader2.getUsername(), leader.getId());
        // then
        Optional<LeaderRequestEntity> result = leaderRequestRepository.findByTeamId(teamId);
        Assertions.assertThat(result).isPresent();
    }

    // 팀 리더 양도 요청 실패 테스트

    @Test
    void leader_transfer_request_failed_when_user_has_max_team() {
        // given
        UserDetail leader = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default1"));
        UserDetail leader2 = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default2"));
        teamAdapter.save(testDataBuilder.createNotPersistedTeam(leader.getId(), "default"));
        Long teamId = teamAdapter.save(testDataBuilder.createNotPersistedTeam(leader2.getId(), "default2"));
        // when
        Throwable result = Assertions.catchThrowable(() -> teamService.createLeaderRequest(teamId, leader.getUsername(), leader2.getId()));
        // then
        Assertions.assertThat(result).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void leader_transfer_request_failed_when_user_is_not_exists() {
        // given
        UserDetail leader = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default1"));
        Long teamId = teamAdapter.save(testDataBuilder.createNotPersistedTeam(leader.getId(), "default"));
        String weirdTarget = "";
        // when
        Throwable result = Assertions.catchThrowable(() -> teamService.createLeaderRequest(teamId, weirdTarget, leader.getId()));
        // then
        Assertions.assertThat(result).isInstanceOf(IllegalArgumentException.class);
    }

    // 팀 리더 양도 거절 성공 테스트

    @Test
    void leader_refuse_success() {

        // given
        UserDetail leader = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default1"));
        UserDetail newLeader = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default2"));

        Long leaderId = leader.getId();
        Long newLeaderId = newLeader.getId();
        Long teamId = teamAdapter.save(testDataBuilder.createNotPersistedTeam(leaderId, "default"));

        teamService.createLeaderRequest(teamId, newLeader.getUsername(), leaderId);
        // when
        teamService.refuseLeaderRequest(teamId, newLeaderId);
        // then
        Optional<LeaderRequestEntity> result = leaderRequestRepository.findByTeamId(teamId);
        Assertions.assertThat(result).isEmpty();
    }


    // 팀 리더 양도 거절 실패 테스트

    @Test
    void leader_refuse_failed_when_user_is_not_target() {
        // given
        UserDetail leader = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default1"));
        UserDetail newLeader = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default2"));

        Long leaderId = leader.getId();
        Long weirdTargetId = 0L;
        Long teamId = teamAdapter.save(testDataBuilder.createNotPersistedTeam(leaderId, "default"));

        teamService.createLeaderRequest(teamId, newLeader.getUsername(), leaderId);
        // when
        Throwable result = Assertions.catchThrowable(() -> teamService.refuseLeaderRequest(teamId, weirdTargetId));
        // then
        Assertions.assertThat(result).isInstanceOf(UnauthorizedAccessException.class);
    }

    // 팀 리더 양도 수락 성공 테스트

    @Test
    void leader_transfer_accept_success() {
        // given
        UserDetail leader = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default1"));
        UserDetail newLeader = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default2"));

        Long leaderId = leader.getId();
        Long newLeaderId = newLeader.getId();
        Long teamId = teamAdapter.save(testDataBuilder.createNotPersistedTeam(leaderId, "default"));

        teamService.createLeaderRequest(teamId, newLeader.getUsername(), leaderId);
        // when
        teamService.changeLeader(teamId, newLeaderId);
        // then
        Team team = teamAdapter.getTeamBy(teamId);

        Assertions.assertThat(team.getLeaderId()).isEqualTo(newLeaderId);
    }

    // 팀 리더 양도 수락 실패 테스트

    //
    @Test
    void leader_transfer_accept_failed_when_user_is_not_requested_target() {
        // given
        UserDetail leader = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default1"));
        UserDetail newLeader = testEntityManager.persist(testDataBuilder.createNotPersistedUser("default2"));

        Long leaderId = leader.getId();
        Long teamId = teamAdapter.save(testDataBuilder.createNotPersistedTeam(leaderId, "default"));
        Long weirdTeamId = 0L;
        teamService.createLeaderRequest(teamId, newLeader.getUsername(), leaderId);
        // when
        Throwable result = Assertions.catchThrowable(() -> teamService.changeLeader(weirdTeamId, newLeader.getId()));
        // then
        Team team = teamAdapter.getTeamBy(teamId);
        Assertions.assertThat(result).isInstanceOf(IllegalArgumentException.class);
    }


}
