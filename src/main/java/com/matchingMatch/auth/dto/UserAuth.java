package com.matchingMatch.auth.dto;

import com.matchingMatch.match.domain.enums.Role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {

	@NotNull
	private Long id;
	@NotNull
	private Role role;

	public static UserAuth team(Long id) {

		return new UserAuth(id, Role.USER);
	}

	public static UserAuth guest() {
		return new UserAuth(-1L, Role.Guest);
	}

}
