package com.matchingMatch.match;

import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.user.domain.UserDetail;

import java.time.LocalDateTime;

public class TestDataBuilder {

    public Match createConfirmedMatch(Long matchId, Team host, Team participant, LocalDateTime confirmedTime) {
        return Match.builder()
                .id(matchId)
                .host(host)
                .participant(participant)
                .startTime(LocalDateTime.of(2024, 11, 20, 10, 0))
                .endTime(LocalDateTime.of(2024, 11, 20, 12, 0))
                .gender(Gender.MALE)
                .stadiumCost(10000)
                .etc("Friendly Match")
                .confirmedTime(confirmedTime)
                .build();
    }

    public Match createDefaultMatch(Long matchId, Team host) {
        return Match.builder()
                .id(matchId)
                .host(host)
                .participant(null)
                .startTime(LocalDateTime.of(2024, 11, 20, 10, 0))
                .endTime(LocalDateTime.of(2024, 11, 20, 12, 0))
                .gender(Gender.MALE)
                .stadiumCost(10000)
                .etc("Friendly Match")
                .build();

    }

    public Team createDefaultTeam(Long teamId, Long leaderId) {
        return Team.builder()
                .id(teamId)
                .name("default")
                .gender(Gender.MALE)
                .teamDescription("default")
                .region("default")
                .teamLogoUrl("default")
                .mannerPointSum(0L)
                .matchCount(0L)
                .leaderId(leaderId)
                .build();
    }

    public Team createNotPersistedTeam(Long leaderId) {
        return Team.builder()
                .name("default")
                .gender(Gender.MALE)
                .teamDescription("default")
                .region("default")
                .teamLogoUrl("default")
                .mannerPointSum(0L)
                .matchCount(0L)
                .leaderId(leaderId)
                .build();
    }

    public UserDetail createNotPersistedUser(String username) {
        return UserDetail.builder()
                .username(username)
                .nickname("default")
                .role(Role.USER)
                .password("default")
                .build();
    }

}
