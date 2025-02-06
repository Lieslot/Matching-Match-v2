package com.matchingMatch.notification.implement;

import com.matchingMatch.notification.domain.MatchNotificationEntity;

public interface MatchNotificationPusher {

	void push(MatchNotificationEntity notification);
}
