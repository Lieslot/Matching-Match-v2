package com.matchingMatch.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserWithDrawRequest {
	@NotNull
	private Long id;

}
