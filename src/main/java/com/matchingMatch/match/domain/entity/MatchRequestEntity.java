package com.matchingMatch.match.domain.entity;


import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchRequestEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long teamId;
    @Column(nullable = false)
    private Long matchId;

    @Builder
    public MatchRequestEntity(Long teamId, Long matchId) {
        this.teamId = teamId;
        this.matchId = matchId;
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

        return other.teamId.equals(teamId) && other.matchId.equals(matchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, matchId);
    }

}
