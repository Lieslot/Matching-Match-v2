package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.Team;
import com.mysql.cj.protocol.x.XProtocolRowInputStream;
import lombok.Getter;

@Getter
public class MannerRateEvent {

    private Match match;
    private Team team;
}
