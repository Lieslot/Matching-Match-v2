package com.matchingMatch.notification.service;


import com.matchingMatch.notification.domain.MatchNotificationEntity;
import com.matchingMatch.notification.domain.MatchNotificationPushAdapter;
import com.matchingMatch.notification.domain.FcmSubscription;
import com.matchingMatch.notification.domain.FcmSubscriptionRepository;
import com.matchingMatch.notification.dto.PublicSubscriptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PushService {

    private final MatchNotificationPushAdapter notificationPushAdapter;
    private final FcmSubscriptionRepository publicSubscriptionRepository;

    public void push(MatchNotificationEntity notification) {
        notificationPushAdapter.push(notification);
    }

    public void subscribe(PublicSubscriptionRequest publicSubscriptionRequest, Long userId) {
        FcmSubscription publicSubscription = FcmSubscription.builder()
                .userId(userId)
                .fcmToken(publicSubscriptionRequest.getFcmToken())
                .build();

        publicSubscriptionRepository.deleteAllByUserId(userId);
        publicSubscriptionRepository.save(publicSubscription);
    }
}
