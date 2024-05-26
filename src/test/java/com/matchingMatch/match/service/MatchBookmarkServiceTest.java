package com.matchingMatch.match.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.MatchBookmark;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(MatchBookmarkService.class)
public class MatchBookmarkServiceTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchBookmarkService matchBookmarkService;

    private Team team;
    private Team otherTeam;
    private Match match;

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

        otherTeam = Team.builder()
                .account("otherTeamAccount")
                .password("otherTeamPassword")
                .teamName("Team B")
                .teamDescription("Description")
                .teamLogoUrl("http://logo.url")
                .region("Region")
                .gender(Gender.MALE)
                .role(Role.USER)
                .build();

        team = teamRepository.save(team);
        otherTeam = teamRepository.save(otherTeam);

        match = Match.builder()
                .host(otherTeam)
                .startTime(
                        LocalDateTime.now())
                .endTime(LocalDateTime.now().plusSeconds(1))
                .stadiumCost(15000)
                .gender(Gender.FEMALE)
                .build();

        match = matchRepository.save(match);
    }

    @AfterEach
    void clear() {
        matchRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    void addMatchBookmarkTest() {
        matchBookmarkService.addMatchBookmark(team.getId(), match.getId());

        Team updatedTeam = teamRepository.findById(team.getId()).orElseThrow();
        assertThat(updatedTeam.getMatchBookmarks()).hasSize(1);

        MatchBookmark bookmark = updatedTeam.getMatchBookmarks().iterator().next();
        assertThat(bookmark.getStoredMatchId()).isEqualTo(match.getId());
        assertThat(bookmark.getTeam()).isEqualTo(team);
    }

    @Test
    void addMatchBookmarkTeamOrMatchDoesNotExistThrowsException() {
        assertThatThrownBy(() -> matchBookmarkService.addMatchBookmark(-1L, match.getId()))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> matchBookmarkService.addMatchBookmark(team.getId(), -1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void removeMatchBookmarkTest() {

        matchBookmarkService.addMatchBookmark(team.getId(), match.getId());
        MatchBookmark bookmark = team.getMatchBookmarks().iterator().next();

        matchBookmarkService.removeMatchBookmark(bookmark.getId(), team.getId());
        assertThat(team.getMatchBookmarks()).isEmpty();
    }

    @Test
    void removeMatchBookmarkTeamDoesNotExistThrowsException() {
        matchBookmarkService.addMatchBookmark(team.getId(), match.getId());
        Team updatedTeam = teamRepository.findById(team.getId()).orElseThrow();
        MatchBookmark bookmark = updatedTeam.getMatchBookmarks().iterator().next();

        assertThatThrownBy(() -> matchBookmarkService.removeMatchBookmark(bookmark.getId(), -1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
