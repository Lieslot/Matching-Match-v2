package com.matchingMatch.match.domain;


import com.matchingMatch.team.domain.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team requestingTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
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

        return other.requestingTeam.equals(requestingTeam) && other.targetMatch.equals(targetMatch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestingTeam, targetMatch);
    }
}
