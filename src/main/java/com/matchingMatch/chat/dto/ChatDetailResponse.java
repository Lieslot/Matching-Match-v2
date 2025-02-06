package com.matchingMatch.chat.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatDetailResponse {

	@NotNull
	private List<ChatDetail> chatDetails;
	@NotNull
	private Long roomId;
	@NotNull
	private String myTeamName;
	@NotNull
	private String myTeamLogoUrl;
	@NotNull
	private String targetTeamName;
	@NotNull
	private String targetTeamLogoUrl;

	@Builder
	public ChatDetailResponse(List<ChatDetail> chatDetails, Long roomId, String myTeamName, String myTeamLogoUrl,
		String targetTeamName, String targetTeamLogoUrl) {
		this.chatDetails = chatDetails;
		this.roomId = roomId;
		this.myTeamName = myTeamName;
		this.myTeamLogoUrl = myTeamLogoUrl;
		this.targetTeamName = targetTeamName;
		this.targetTeamLogoUrl = targetTeamLogoUrl;
	}

}
