package com.matchingMatch.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
public class GetChatRoomPreviewResponse {
    @NotNull
    List<ChatRoomPreview> previews;
}
