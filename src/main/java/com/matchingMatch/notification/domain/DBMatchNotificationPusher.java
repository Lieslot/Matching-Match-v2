package com.matchingMatch.notification.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBMatchNotificationPusher implements MatchNotificationPusher {

    private final MatchNotificationRepository notificationRepository;

    public void push(MatchNotificationEntity notification) {
        notificationRepository.save(notification);
    }
}
