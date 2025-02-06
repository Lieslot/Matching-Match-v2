package com.matchingMatch.notification.service;

import org.springframework.stereotype.Service;

import com.matchingMatch.notification.domain.FcmSubscriptionRepository;
import com.matchingMatch.notification.domain.entity.FcmSubscription;
import com.matchingMatch.notification.dto.PublicSubscriptionRequest;
import com.matchingMatch.notification.implement.MatchNotificationPushAdapter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final MatchNotificationPushAdapter notificationPushAdapter;
	private final FcmSubscriptionRepository fcmSubscriptionRepository;

	public void subscribe(PublicSubscriptionRequest publicSubscriptionRequest, Long userId) {
		FcmSubscription publicSubscription = FcmSubscription.builder()
			.userId(userId)
			.fcmToken(publicSubscriptionRequest.getFcmToken())
			.build();

		fcmSubscriptionRepository.deleteAllByUserId(userId);
		fcmSubscriptionRepository.save(publicSubscription);
	}
}
