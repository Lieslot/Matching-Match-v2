package com.matchingMatch.notification.domain;

import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
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
