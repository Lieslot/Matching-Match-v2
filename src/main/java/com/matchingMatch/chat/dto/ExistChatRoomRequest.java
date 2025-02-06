package com.matchingMatch.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExistChatRoomRequest {
	@NotNull
	private Long roomId;
	@NotNull
	private Long teamId;

	@Builder
	public ExistChatRoomRequest(Long roomId, Long userId) {
		this.roomId = roomId;
		this.teamId = userId;
	}

}
