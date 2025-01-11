package com.matchingMatch.match.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MatchRefuseRequest {
    @NotNull
    private Long matchRequestId;
}
