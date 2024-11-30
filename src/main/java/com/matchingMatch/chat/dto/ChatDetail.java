package com.matchingMatch.chat.dto;

import com.matchingMatch.chat.entity.ChatType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatDetail {

    private Long id;
    private Long teamId;
    private ChatType chatType;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public ChatDetail(Long id, Long teamId, ChatType chatType, String content, LocalDateTime createdAt) {
        this.id = id;
        this.teamId = teamId;
        this.chatType = chatType;
        this.content = content;
        this.createdAt = createdAt;
    }

}
