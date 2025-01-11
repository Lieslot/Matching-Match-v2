package com.matchingMatch.match.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MatchCancelConfirmedRequest {
    @NotNull
    private Long matchId;

}
