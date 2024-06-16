package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.Match;
import com.matchingMatch.team.domain.Team;
import lombok.Getter;

@Getter
public class MannerRateEvent {

    private Match match;
    private Team team;
}
