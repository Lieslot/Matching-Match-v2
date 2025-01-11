package com.matchingMatch.match.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MannerRateRequest {

    @NotNull
    private Long matchId;
    @NotNull
    private Long rate;
}
