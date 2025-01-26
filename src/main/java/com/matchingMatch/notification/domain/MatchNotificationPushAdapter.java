package com.matchingMatch.notification.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MatchNotificationPushAdapter {

    private final List<MatchNotificationPusher> notificationPusher;


    public void push(MatchNotificationEntity notification) {
        notificationPusher.forEach(pusher -> pusher.push(notification));
    }
}
