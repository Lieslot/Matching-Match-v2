package com.matchingMatch.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matchingMatch.chat.entity.ChatType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class SendChatRequest {

    private Long roomId = -1L;

    private String content;

    private Long teamId;

    private Long targetTeamId;

    public boolean hasRoomId() {
        return roomId != -1L;
    }

}
