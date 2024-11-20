package com.matchingMatch.match.exception;

import com.matchingMatch.match.domain.Match;

public class MatchAlreadyConfirmedException extends IllegalArgumentException{

    // matchid도 포함해서 예외처리
    private static final String message = "이미 확정된 매치입니다.";

    public MatchAlreadyConfirmedException(Long matchId) {
        super(message+"id: "+matchId);
    }
}
