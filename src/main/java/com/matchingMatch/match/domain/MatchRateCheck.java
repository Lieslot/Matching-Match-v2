package com.matchingMatch.match.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
public class MatchRateCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "match_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Match matchId;

    private Boolean isHostCheck;

    private Boolean isParticipantCheck;

    public MatchRateCheck() {
        this.isHostCheck = false;
        this.isParticipantCheck = false;
    }

    public void checkParticipant() {
        isParticipantCheck = true;
    }
    public void checkHost() {
        isHostCheck = true;
    }
}
