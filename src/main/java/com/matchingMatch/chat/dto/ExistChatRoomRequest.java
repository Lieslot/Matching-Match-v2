package com.matchingMatch.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExistChatRoomRequest {
    private Long roomId;
    private Long teamId;

    @Builder
    public ExistChatRoomRequest(Long roomId, Long userId) {
        this.roomId = roomId;
        this.teamId = userId;
    }

}
