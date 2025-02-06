package com.matchingMatch.chat.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetChatRoomPreviewResponse {
	@NotNull
	List<ChatRoomPreview> previews;
}
