package com.matchingMatch.notification.implement;

import com.matchingMatch.notification.domain.MatchNotificationEntity;
import com.matchingMatch.notification.domain.MatchNotificationRepository;
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
