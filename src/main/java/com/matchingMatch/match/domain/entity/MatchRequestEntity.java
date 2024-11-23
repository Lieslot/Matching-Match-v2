package com.matchingMatch.match.domain.entity;


import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchRequestEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long sendTeamId;
    @Column(nullable = false)
    private Long matchId;
    @Column(nullable = false)
    private Long targetTeamId;

    @Builder
    public MatchRequestEntity(Long teamId, Long matchId, Long targetTeamId) {
        this.sendTeamId = teamId;
        this.matchId = matchId;
        this.targetTeamId = targetTeamId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        MatchRequestEntity other = (MatchRequestEntity) obj;

        return other.sendTeamId.equals(sendTeamId) && other.matchId.equals(matchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sendTeamId, matchId);
    }

    public Boolean hasTeam(Long teamId) {
        return sendTeamId.equals(teamId) || targetTeamId.equals(teamId);
    }

    public Boolean hasSendTeam(Long teamId) {
        return sendTeamId.equals(teamId);
    }

    public Boolean hasTargetTeam(Long teamId) {
        return targetTeamId.equals(teamId);
    }

    public void checkCancelDeadline() {
        LocalDateTime deadline = this.getCreatedAt().plusMinutes(10);
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(deadline)) {
            long minute = ChronoUnit.MINUTES.between(now, deadline);
            long second = ChronoUnit.SECONDS.between(now, deadline);

            throw new IllegalArgumentException(String.format("%d분 %초 후에 취소 가능합니다.", minute, second));
        }
    }

}
