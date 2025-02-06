package com.matchingMatch.match.exception;

import jakarta.validation.constraints.NotNull;

public class MatchAlreadyRatedException extends IllegalArgumentException {

	private static final String MESSAGE = "이미 평가한 매치입니다.";

	public MatchAlreadyRatedException(@NotNull Long leaderId) {
		super(MESSAGE + " userId: " + leaderId);
	}
}
