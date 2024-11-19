package com.matchingMatch.match.domain.entity;

import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class MannerRateCheckEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long matchId;

    @Column(nullable = false)
    private Boolean isParticipantRate;

    @Column(nullable = false)
    private Boolean isHostRate;

    public void rateHost() {
        isHostRate = true;
    }

    public void rateParticipantRate() {
        isParticipantRate = true;
    }

    public Boolean hasOver() {
        return isHostRate && isParticipantRate;
    }

    @Builder
    public MannerRateCheckEntity(Long matchId) {
        this.matchId = matchId;
        this.isHostRate = false;
        this.isParticipantRate = false;
    }

}
