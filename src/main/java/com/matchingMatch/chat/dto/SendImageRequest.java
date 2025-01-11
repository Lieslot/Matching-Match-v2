package com.matchingMatch.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SendImageRequest {
    @NotNull
    private Long roomId = -1L;
    @NotNull
    private Long teamId;
    @NotNull
    private Long targetTeamId;

}
