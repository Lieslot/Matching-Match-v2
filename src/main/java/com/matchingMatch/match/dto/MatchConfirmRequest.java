package com.matchingMatch.match.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MatchConfirmRequest {
	@NotNull
	private Long postId;
	@NotNull
	private Long requestingTeamId;

}
