package com.matchingMatch.chat.dto;

import lombok.Getter;

@Getter
public class SendImageRequest {

    private Long roomId = -1L;

    private Long teamId;

    private Long targetTeamId;

}
