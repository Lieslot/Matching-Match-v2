package com.matchingMatch.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SendChatResponse {
	@NotNull
	private Long id;

}
