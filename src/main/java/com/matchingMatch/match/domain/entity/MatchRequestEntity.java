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

}
