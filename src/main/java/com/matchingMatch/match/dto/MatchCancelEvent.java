package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.team.domain.entity.TeamEntity;
import lombok.Getter;

@Getter
public class MatchCancelEvent {

    private TeamEntity team;
    private MatchEntity match;

    public MatchCancelEvent(TeamEntity team, MatchEntity match) {
        this.team = team;
        this.match = match;
    }
}
