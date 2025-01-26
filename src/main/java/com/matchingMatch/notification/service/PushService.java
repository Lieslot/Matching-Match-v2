package com.matchingMatch.notification.service;


import com.matchingMatch.notification.domain.MatchNotificationEntity;
import com.matchingMatch.notification.domain.MatchNotificationPushAdapter;
import com.matchingMatch.notification.domain.PublicSubscription;
import com.matchingMatch.notification.domain.PublicSubscriptionRepository;
import com.matchingMatch.notification.dto.PublicSubscriptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PushService {

    private final MatchNotificationPushAdapter notificationPushAdapter;
    private final PublicSubscriptionRepository publicSubscriptionRepository;

    public void push(MatchNotificationEntity notification) {
        notificationPushAdapter.push(notification);
    }

    public void subscribe(PublicSubscriptionRequest publicSubscriptionRequest, Long userId) {
        PublicSubscription publicSubscription = PublicSubscription.builder().
                userId(userId).
                endpoint(publicSubscriptionRequest.getEndpoint()).
                auth(publicSubscriptionRequest.getKeys().getAuth()).
                p256dh(publicSubscriptionRequest.getKeys().getP256dh()).
                build();
        publicSubscriptionRepository.save(publicSubscription);
    }
}
