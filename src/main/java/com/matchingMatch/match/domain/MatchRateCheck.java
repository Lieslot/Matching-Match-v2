package com.matchingMatch.match.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
public class MatchRateCheck {

    @Id
    @OneToOne(targetEntity = Match.class)
    private Long matchId;

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
