package com.matchingMatch.admin.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserListResponse {

	@NotNull
	private List<UserListElement> userList;

	public UserListResponse(List<UserListElement> userList) {
		this.userList = userList;
	}

}
