package com.matchingMatch.match.exception;

public class HostNotFoundException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "호스트를 찾을 수 없습니다.";

    public HostNotFoundException(Long id) {
        super(ERROR_MESSAGE + " id: " + id);

    }
}
