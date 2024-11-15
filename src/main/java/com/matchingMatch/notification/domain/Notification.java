package com.matchingMatch.notification.domain;

import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Column;
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
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long targetTeamId;

    @Column(nullable = false)
    private Long targetMatchId;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;


    @Builder
    public Notification(Long targetTeamId, Long targetMatchId, NotificationType notificationType) {
        this.targetTeamId = targetTeamId;
        this.targetMatchId = targetMatchId;
        this.notificationType = notificationType;
    }
}
