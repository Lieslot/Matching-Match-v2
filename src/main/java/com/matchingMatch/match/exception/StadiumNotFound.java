package com.matchingMatch.match.exception;

public class StadiumNotFound extends IllegalArgumentException {

	private static final String ERROR_MESSAGE = "경기장을 찾을 수 없습니다.";

	public StadiumNotFound(Long id) {
		super(ERROR_MESSAGE + " id: " + id);

	}
}
