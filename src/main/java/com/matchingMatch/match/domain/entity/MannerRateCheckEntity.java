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
import lombok.Setter;

@Getter
@Setter
@Entity(name = "manner_rate_check")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MannerRateCheckEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long matchId;

    @Column(nullable = false)
    private Boolean isParticipantRate = false;

    @Column(nullable = false)
    private Boolean isHostRate = false;

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
    public MannerRateCheckEntity(Long matchId, Boolean isParticipantRate, Boolean isHostRate) {
        this.matchId = matchId;
        this.isHostRate = isHostRate;
        this.isParticipantRate = isParticipantRate;
    }

    public static MannerRateCheckEntity from(Long matchId) {
        return MannerRateCheckEntity.builder()
                .matchId(matchId)
                .isHostRate(false)
                .isParticipantRate(false)
                .build();
    }


}
