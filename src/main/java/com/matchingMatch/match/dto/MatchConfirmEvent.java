package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.team.domain.entity.TeamEntity;
import lombok.Getter;

@Getter
public class MatchConfirmEvent {

    private TeamEntity team;
    private MatchEntity match;

    public MatchConfirmEvent(TeamEntity team, MatchEntity match) {
        this.team = team;
        this.match = match;
    }
}
