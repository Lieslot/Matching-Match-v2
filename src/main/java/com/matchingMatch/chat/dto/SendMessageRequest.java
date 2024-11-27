package com.matchingMatch.chat.dto;

import lombok.Getter;

@Getter
public class SendMessageRequest {

    private Long roomId = -1L;

    private String content;

    private Long teamId;

    private Long targetTeamId;

    public boolean hasRoomId() {
        return roomId != -1L;
    }

}
