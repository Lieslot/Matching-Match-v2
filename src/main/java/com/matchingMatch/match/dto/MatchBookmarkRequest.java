package com.matchingMatch.match.dto;

import lombok.Getter;

@Getter
public class MatchBookmarkRequest {

    private Long matchId;

    public MatchBookmarkRequest(Long matchId) {
        this.matchId = matchId;
    }
}
