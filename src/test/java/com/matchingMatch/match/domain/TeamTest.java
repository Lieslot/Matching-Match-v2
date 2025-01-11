package com.matchingMatch.match.domain;


import com.matchingMatch.TestDataBuilder;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.dto.TeamProfileUpdateRequest;
import com.matchingMatch.team.domain.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class TeamTest {

    private TestDataBuilder testDataBuilder = new TestDataBuilder();


    @Test
    void update_team_profile() {
        // given

        Team team = testDataBuilder.createDefaultTeam(1L, 1L);
        TeamProfileUpdateRequest teamProfileUpdateRequest = new TeamProfileUpdateRequest();
        teamProfileUpdateRequest.setTeamDescription("new description");
        teamProfileUpdateRequest.setTeamLogoUrl("new logo url");
        teamProfileUpdateRequest.setTeamName("new name");
        teamProfileUpdateRequest.setRegion("new region");
        teamProfileUpdateRequest.setGender(Gender.MALE);

        // when
        team.updateTeamProfile(teamProfileUpdateRequest);
        // then

        Assertions.assertThat(team.getDescription()).isEqualTo("new description");
        Assertions.assertThat(team.getLogoUrl()).isEqualTo("new logo url");
        Assertions.assertThat(team.getName()).isEqualTo("new name");
        Assertions.assertThat(team.getRegion()).isEqualTo("new region");
        Assertions.assertThat(team.getGender()).isEqualTo(Gender.MALE);
    }
}
