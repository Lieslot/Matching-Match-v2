package com.matchingMatch.match.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MatchCancelRequest {
	@NotNull
	private Long matchRequestId;

}
