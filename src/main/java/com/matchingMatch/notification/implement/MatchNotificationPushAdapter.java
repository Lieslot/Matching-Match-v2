package com.matchingMatch.notification.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import com.matchingMatch.notification.domain.MatchNotificationEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MatchNotificationPushAdapter {

	private final List<MatchNotificationPusher> notificationPusher;

	public void push(MatchNotificationEntity notification) {
		notificationPusher.forEach(pusher -> pusher.push(notification));
	}
}
