package com.matchingMatch.listener;


import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.listener.event.MatchCancelEvent;
import com.matchingMatch.listener.event.MatchConfirmEvent;
import com.matchingMatch.listener.event.MatchRequestEvent;
import com.matchingMatch.notification.domain.MatchNotificationEntity;
import com.matchingMatch.notification.domain.MatchNotificationPushAdapter;
import com.matchingMatch.notification.domain.MatchNotificationType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationEventListener.class);
    private final TeamAdapter teamAdapter;
    private final MatchNotificationPushAdapter notificationPusher;



    @Async
    @EventListener
    public void publishMatchConfirmNotification(MatchConfirmEvent matchConfirmEvent) {

        Long matchId = matchConfirmEvent.getMatchId();
        Long targetTeamId = matchConfirmEvent.getTargetTeamId();
        Long sendTeamId = matchConfirmEvent.getSendTeamId();

        MatchNotificationEntity notification = MatchNotificationEntity.builder()
                .targetMatchId(matchId)
                .targetTeamId(targetTeamId)
                .sendTeamId(sendTeamId)
                .notificationType(MatchNotificationType.MATCH_CONFIRM)
                .build();

        notificationPusher.push(notification);

    }

    @Async
    @EventListener
    public void publishConfirmedMatchCancelNotification(MatchCancelEvent confirmedMatchCancelEvent) {

        Long matchId = confirmedMatchCancelEvent.getMatchId();
        Long targetTeamId = confirmedMatchCancelEvent.getTargetTeamId();
        Long sendTeamId = confirmedMatchCancelEvent.getSendTeamId();

        MatchNotificationEntity notification = MatchNotificationEntity.builder()
                .targetMatchId(matchId)
                .targetTeamId(targetTeamId)
                .sendTeamId(sendTeamId)
                .notificationType(MatchNotificationType.MATCH_CONFIRM_CANCEL)
                .build();

        notificationPusher.push(notification);
    }


    @Async
    @EventListener
    public void publishMatchRequestNotification(MatchRequestEvent matchRequestEvent) {

        Long matchId = matchRequestEvent.getMatchId();
        Long sendTeamId = matchRequestEvent.getSendTeamId();
        Long targetTeamId = matchRequestEvent.getTargetTeamId();

        MatchNotificationEntity notification = MatchNotificationEntity.builder()
                .targetMatchId(matchId)
                .targetTeamId(targetTeamId)
                .sendTeamId(sendTeamId)
                .notificationType(MatchNotificationType.MATCH_REQUEST)
                .build();

        notificationPusher.push(notification);
    }


}
