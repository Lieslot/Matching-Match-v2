package com.matchingMatch.match.domain;

import java.util.Objects;

public record MannerRate(Long userId, Long rate) {
    public MannerRate {
        if (rate < 1 || rate > 5) {
            throw new IllegalArgumentException("평점은 1점부터 5점까지 가능합니다.");
        }
        Objects.requireNonNull(userId, "userId는 null일 수 없습니다.");
        Objects.requireNonNull(rate, "rate는 null일 수 없습니다.");
    }

    public boolean isRater(Long userId) {
        return this.userId.equals(userId);
    }

}
