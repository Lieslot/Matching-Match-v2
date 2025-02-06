package com.matchingMatch.notification.dto;

import java.time.LocalDateTime;

import com.matchingMatch.notification.domain.entity.MatchNotificationType;

import lombok.Getter;

@Getter
public class NotificationResponse {

	private Long id;
	private Long teamId;
	private Long matchId;
	private MatchNotificationType notificationType;
	private Boolean isRead = false;
	private LocalDateTime createdAt;

	public NotificationResponse(Long teamId, Long matchId, MatchNotificationType notificationType, Boolean isRead,
		LocalDateTime createdAt) {
		this.teamId = teamId;
		this.matchId = matchId;
		this.notificationType = notificationType;
		this.isRead = isRead;
		this.createdAt = createdAt;
	}

}
