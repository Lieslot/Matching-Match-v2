package com.matchingMatch.chat.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BlockChatUserRequest {
    @NotNull
    private Long teamId;
}
