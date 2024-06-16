package com.matchingMatch.match.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.matchingMatch.team.domain.Team;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.dto.TeamProfileResponse;
import com.matchingMatch.match.dto.TeamProfileUpdateRequest;
import com.matchingMatch.team.service.TeamService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(TeamService.class)
class TeamServiceTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamService teamService;

    private Team team;
    private TeamProfileUpdateRequest updateRequest;

    @BeforeEach
    void setUp() {
        team = Team.builder()
                .account("teamAccount")
                .password("teamPassword")
                .teamName("Team A")
                .teamDescription("Description")
                .teamLogoUrl("http://logo.url")
                .region("Region")
                .gender(Gender.MALE)
                .role(Role.USER)
                .build();

        teamRepository.save(team);

        updateRequest = new TeamProfileUpdateRequest();
        updateRequest.setTeamName("Updated Team Name");
        updateRequest.setTeamLogoUrl("http://updated.logo.url");
        updateRequest.setTeamDescription("Updated Description");
        updateRequest.setRegion("Updated Region");
        updateRequest.setGender(Gender.FEMALE);
    }

    @AfterEach
    void clear() {
        teamRepository.deleteAll();
    }

    @Test
    void getTeamProfileTeamExistsReturnsProfile() {
        TeamProfileResponse response = teamService.getTeamProfile(team.getId());

        assertThat(response).isNotNull();
        assertThat(response.getTeamName()).isEqualTo("Team A");
        assertThat(response.getTeamLogoUrl()).isEqualTo("http://logo.url");
        assertThat(response.getTeamDescription()).isEqualTo("Description");
        assertThat(response.getRegion()).isEqualTo("Region");
        assertThat(response.getGender()).isEqualTo(Gender.MALE);
    }

    @Test
    void getTeamProfileTeamDoesNotExistThrowsException() {
        assertThatThrownBy(() -> teamService.getTeamProfile(-1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateTeamProfileTeamExistsUpdatesProfile() {
        teamService.updateTeamProfile(team.getId(), updateRequest);

        Team updatedTeam = teamRepository.findById(team.getId()).orElseThrow();

        assertThat(updatedTeam.getTeamName()).isEqualTo("Updated Team Name");
        assertThat(updatedTeam.getTeamLogoUrl()).isEqualTo("http://updated.logo.url");
        assertThat(updatedTeam.getTeamDescription()).isEqualTo("Updated Description");
        assertThat(updatedTeam.getRegion()).isEqualTo("Updated Region");
        assertThat(updatedTeam.getGender()).isEqualTo(Gender.FEMALE);
    }

    @Test
    void updateTeamProfileTeamDoesNotExistThrowsException() {
        assertThatThrownBy(() -> teamService.updateTeamProfile(-1L, updateRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
