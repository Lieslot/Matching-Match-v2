package com.matchingMatch.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BlockedUserResponse {

    private final Long id;
    private final String nickname;

    @Builder
    public BlockedUserResponse(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
