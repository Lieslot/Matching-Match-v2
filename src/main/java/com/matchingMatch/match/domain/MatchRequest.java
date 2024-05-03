package com.matchingMatch.match.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class MatchRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team requestingTeam;

    @OneToOne(fetch = FetchType.LAZY)
    private Match targetMatch;

    @Builder
    public MatchRequest(Team requestingTeam, Match targetMatch) {
        this.requestingTeam = requestingTeam;
        this.targetMatch = targetMatch;
    }

    public MatchRequest() {

    }
}
