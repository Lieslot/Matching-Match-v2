package com.matchingMatch.notification.domain;

import com.matchingMatch.match.domain.BaseEntity;
import com.matchingMatch.notification.domain.entity.MatchNotificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchNotificationEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long targetTeamId;

	private Long sendTeamId;

	private Long targetMatchId;

	@Enumerated(EnumType.STRING)
	private MatchNotificationType notificationType;

	private Boolean isRead = false;

	@Builder
	public MatchNotificationEntity(Long targetTeamId, Long targetMatchId, MatchNotificationType notificationType,
		Boolean isRead, Long sendTeamId) {
		this.targetTeamId = targetTeamId;
		this.targetMatchId = targetMatchId;
		this.notificationType = notificationType;
		this.isRead = isRead;
		this.sendTeamId = sendTeamId;
	}
}
