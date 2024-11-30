package com.matchingMatch.chat.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomPreview {

    private Long roomId;
    private String teamLogoUrl;
    private String teamName;
    private String lastChat;
    private LocalDateTime lastChatTime;

    @Builder
    public ChatRoomPreview(Long roomId, String teamLogoUrl, String teamName, String lastChat, LocalDateTime lastChatTime) {
        this.roomId = roomId;
        this.teamLogoUrl = teamLogoUrl;
        this.teamName = teamName;
        this.lastChat = lastChat;
        this.lastChatTime = lastChatTime;
    }
}
