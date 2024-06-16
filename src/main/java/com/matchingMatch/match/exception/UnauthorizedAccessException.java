package com.matchingMatch.match.exception;

public class UnauthorizedAccessException extends IllegalArgumentException {

    private static final String ERROR_MEESAGE = "권한이 없는 접근입니다.";

    public UnauthorizedAccessException() {

        super(ERROR_MEESAGE);
    }
}
