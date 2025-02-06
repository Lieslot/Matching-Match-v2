package com.matchingMatch.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SendMessageRequest {
	@NotNull
	private Long roomId = -1L;
	@NotNull
	private String content;
	@NotNull
	private Long teamId;
	@NotNull
	private Long targetTeamId;

	@NotNull
	public boolean hasRoomId() {
		return roomId != -1L;
	}

}
