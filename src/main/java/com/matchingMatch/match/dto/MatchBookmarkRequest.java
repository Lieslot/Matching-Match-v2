package com.matchingMatch.match.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MatchBookmarkRequest {
    @NotNull
    private Long matchId;

    public MatchBookmarkRequest(Long matchId) {
        this.matchId = matchId;
    }
}
