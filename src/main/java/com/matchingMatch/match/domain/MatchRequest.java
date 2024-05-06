package com.matchingMatch.match.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Include
    @ManyToOne(fetch = FetchType.LAZY)
    private Team requestingTeam;

    @Include
    @ManyToOne(fetch = FetchType.LAZY)
    private Match targetMatch;

    @Builder
    public MatchRequest(Team requestingTeam, Match targetMatch) {
        this.requestingTeam = requestingTeam;
        this.targetMatch = targetMatch;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        MatchRequest other = (MatchRequest) obj;

        return other.requestingTeam.equals(requestingTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestingTeam, targetMatch);
    }
}
