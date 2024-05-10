package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.Team;
import lombok.Getter;

@Getter
public class MatchConfirmEvent {

    private Team team;
    private Match match;
}
