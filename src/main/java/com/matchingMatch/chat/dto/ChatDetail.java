package com.matchingMatch.chat.dto;

import java.time.LocalDateTime;

import com.matchingMatch.chat.entity.ChatType;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatDetail {
	@NotNull
	private Long id;
	@NotNull
	private Long teamId;
	@NotNull
	private ChatType chatType;
	@NotNull
	private String content;
	@NotNull
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
