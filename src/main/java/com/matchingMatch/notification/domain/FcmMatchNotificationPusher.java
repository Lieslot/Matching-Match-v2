package com.matchingMatch.notification.domain;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.notification.common.NotificationTemplate;
import com.matchingMatch.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
@Slf4j
@RequiredArgsConstructor
public class FcmMatchNotificationPusher {

    private final TeamAdapter teamAdapter;
    private final PublicSubscriptionRepository publicSubscriptionRepository;


    public void push(MatchNotificationEntity matchNotification) {

        Team targetTeam = teamAdapter.getTeamBy(matchNotification.getSendTeamId());
        PublicSubscription subscription = publicSubscriptionRepository.findByUserId(targetTeam.getLeaderId()).orElseThrow(
                () -> new IllegalArgumentException("Subscription not found")
        );


        NotificationTemplate template = NotificationTemplate.findTemplateByType(matchNotification.getNotificationType());
        // TODO teamAdapter 안쓰는 방법으로 수정
        String message = MessageFormat.format(template.getMessageTemplate(), targetTeam.getName());
        String title = template.getTitle();

        WebpushConfig webpushConfig = WebpushConfig.builder()
                .setNotification(
                        WebpushNotification.builder()
                                .setTitle(title)
                                .setBody(message)
                                .build()
                ).build();

        // TODO FCM 전송
        Message fcmMessage = Message.builder()
                .setWebpushConfig(webpushConfig)
                .setToken(subscription.getEndpoint())
                .build();


        try {
            FirebaseMessaging.getInstance().send(fcmMessage);
        } catch (FirebaseMessagingException e) {
            log.error("FCM message sending has failed", e);
        }
    }
}
