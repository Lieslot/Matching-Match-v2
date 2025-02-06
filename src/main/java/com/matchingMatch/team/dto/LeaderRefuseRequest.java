package com.matchingMatch.team.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LeaderRefuseRequest {

	@NotNull
	private Long teamId;
}
