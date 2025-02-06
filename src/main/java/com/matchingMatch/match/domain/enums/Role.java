package com.matchingMatch.match.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	ADMIN("관리자"),
	USER("유저"),
	Guest("게스트");

	private final String name;

}
