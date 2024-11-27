package com.matchingMatch.chat.implement;

import com.matchingMatch.chat.entity.ChatType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Chat {

    private Long id;
    private Long roomId;
    private Long sendTeamId;
    private String content;
    private ChatType chatType;

    @Builder
    public Chat(Long id, Long roomId, Long sendTeamId, String content, ChatType chatType) {
        this.id = id;
        this.roomId = roomId;
        this.sendTeamId = sendTeamId;
        this.content = content;
        this.chatType = chatType;
    }
}
