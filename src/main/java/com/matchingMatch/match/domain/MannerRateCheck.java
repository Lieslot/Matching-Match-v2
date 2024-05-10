package com.matchingMatch.match.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Getter
@Entity
public class MannerRateCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Match match;
    private Boolean isHostCheck = false;

    private Boolean isParticipantCheck = false;

    public void checkHost() {
        isHostCheck = true;
    }
    public void checkParticipant() {
        isParticipantCheck = true;
    }

}
