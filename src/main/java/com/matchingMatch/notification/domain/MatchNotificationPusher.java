package com.matchingMatch.notification.domain;

public interface MatchNotificationPusher {

    void push(MatchNotificationEntity notification);
}
