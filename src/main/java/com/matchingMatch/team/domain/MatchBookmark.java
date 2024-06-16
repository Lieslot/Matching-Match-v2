package com.matchingMatch.team.domain;

import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchBookmark extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storedMatchId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @Builder
    public MatchBookmark(Long storedMatchId, Team team) {
        this.storedMatchId = storedMatchId;
        this.team = team;
    }
}
