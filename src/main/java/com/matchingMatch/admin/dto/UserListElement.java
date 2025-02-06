package com.matchingMatch.admin.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserListElement {

	@NotNull(message = "유저 아이디는 필수입니다.")
	private Long id;
	@NotNull
	private String username;
	@NotNull
	private String nickname;
	@NotNull
	private LocalDate banDeadLine;

	@Builder
	public UserListElement(Long id, String username, String nickname, LocalDate banDeadLine) {
		this.id = id;
		this.username = username;
		this.nickname = nickname;
		this.banDeadLine = banDeadLine;
	}

}
