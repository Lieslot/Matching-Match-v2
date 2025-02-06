package com.matchingMatch.match.exception;

public class MatchNotFoundException extends IllegalArgumentException {

	private static final String ERROR_MESSAGE = "매치를 찾을 수 없습니다.";

	public MatchNotFoundException(Long id) {
		super(ERROR_MESSAGE + " id: " + id);

	}
}
