package com.matchingMatch.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomPreview {
    @NotNull
    private Long roomId;
    @NotNull
    private Long myTeamId;
    @NotNull
    private Long targetTeamId;
    @NotNull
    private String teamLogoUrl;
    @NotNull
    private String teamName;
    @NotNull
    private String lastChat;
    @NotNull
    private LocalDateTime lastChatTime;

    @Builder
    public ChatRoomPreview(Long roomId, String teamLogoUrl, String teamName, String lastChat, LocalDateTime lastChatTime
    , Long myTeamId, Long targetTeamId) {
        this.roomId = roomId;
        this.teamLogoUrl = teamLogoUrl;
        this.teamName = teamName;
        this.lastChat = lastChat;
        this.lastChatTime = lastChatTime;
        this.myTeamId = myTeamId;
        this.targetTeamId = targetTeamId;
    }
}
