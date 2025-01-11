package com.matchingMatch.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BlockedUserResponse {
    @NotNull
    private final Long id;
    @NotNull
    private final String nickname;

    @Builder
    public BlockedUserResponse(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
