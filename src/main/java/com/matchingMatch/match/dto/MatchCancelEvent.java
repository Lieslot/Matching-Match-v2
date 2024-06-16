package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.Match;
import com.matchingMatch.team.domain.Team;
import lombok.Getter;

@Getter
public class MatchCancelEvent {

    private Team team;
    private Match match;

    public MatchCancelEvent(Team team, Match match) {
        this.team = team;
        this.match = match;
    }
}
