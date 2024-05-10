package com.matchingMatch.listener;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.dto.MannerRateEvent;
import com.matchingMatch.match.dto.MatchConfirmEvent;
import com.matchingMatch.notification.domain.Notification;
import com.matchingMatch.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {


    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void publishMannerRateNotification(MannerRateEvent mannerRateEvent) {
        Match match = mannerRateEvent.getMatch();
        Team team = mannerRateEvent.getTeam();

        Notification notification = Notification.builder()
                                                .targetMatch(match)
                                                .targetTeam(team)
                                                .notificationType(NotificationType.MANNER_RATE)
                                                .build();

        team.addNotification(notification);

    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void publishMatchConfirmNotification(MatchConfirmEvent matchConfirmEvent) {

        Match match = matchConfirmEvent.getMatch();
        Team team = matchConfirmEvent.getTeam();

        Notification notification = Notification.builder()
                                                                        .targetMatch(match)
                                                                        .targetTeam(team)
                .notificationType(NotificationType.MATCH_CONFIRM)
                                                                        .build();

        team.addNotification(notification);
    }


}
