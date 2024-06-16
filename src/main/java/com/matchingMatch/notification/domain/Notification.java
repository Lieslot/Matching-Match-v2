package com.matchingMatch.notification.domain;

import com.matchingMatch.match.domain.Match;
import com.matchingMatch.team.domain.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_team_id")
    private Team targetTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_match_id")
    private Match targetMatch;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;


    @Builder
    public Notification(Team targetTeam, Match targetMatch, NotificationType notificationType) {
        this.targetTeam = targetTeam;
        this.targetMatch = targetMatch;
        this.notificationType = notificationType;
    }
}
