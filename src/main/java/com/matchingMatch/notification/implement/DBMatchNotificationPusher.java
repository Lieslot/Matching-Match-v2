package com.matchingMatch.notification.implement;

import org.springframework.stereotype.Component;

import com.matchingMatch.notification.domain.MatchNotificationEntity;
import com.matchingMatch.notification.domain.MatchNotificationRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DBMatchNotificationPusher implements MatchNotificationPusher {

	private final MatchNotificationRepository notificationRepository;

	public void push(MatchNotificationEntity notification) {
		notificationRepository.save(notification);
	}
}
