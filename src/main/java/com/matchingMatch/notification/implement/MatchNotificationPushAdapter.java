package com.matchingMatch.notification.implement;

import com.matchingMatch.notification.domain.MatchNotificationEntity;
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
