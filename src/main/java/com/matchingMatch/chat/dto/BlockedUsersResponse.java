package com.matchingMatch.chat.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BlockedUsersResponse {

	@NotNull
	List<BlockedUserResponse> blockedUserResponses;

	public BlockedUsersResponse(List<BlockedUserResponse> blockedUserResponses) {
		this.blockedUserResponses = blockedUserResponses;
	}
}
