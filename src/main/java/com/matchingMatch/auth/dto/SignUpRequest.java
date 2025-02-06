package com.matchingMatch.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignUpRequest {
	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String nickname;

}
