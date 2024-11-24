package com.matchingMatch.team.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LeaderChangeRequest {

    @NotNull
    private Long teamId;

}
