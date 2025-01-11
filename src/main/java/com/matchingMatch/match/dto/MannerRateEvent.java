package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.team.domain.entity.TeamEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MannerRateEvent {

    private MatchEntity match;
    private TeamEntity team;
}
