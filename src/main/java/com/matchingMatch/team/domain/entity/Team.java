package com.matchingMatch.team.domain.entity;

import com.matchingMatch.match.domain.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Team {


    private Long id;

    @NotNull
    private String name;

    private String teamDescription;
    private String teamLogoUrl;

    @NotNull
    private Long leaderId;

    private Long mannerPointSum = 0L;
    private Long matchCount = 0L;
    @NotNull
    private String region;

    @NotNull
    private Gender gender;

    private List<Long> confirmedMatchIds = new ArrayList<>();

    @Builder
    public Team(Long id, Gender gender, String name, String teamDescription, String teamLogoUrl, Long leaderId, Long mannerPointSum, Long matchCount, String region, List<Long> confirmedMatchIds) {
        this.id = id;
        this.name = name;
        this.teamDescription = teamDescription;
        this.teamLogoUrl = teamLogoUrl;
        this.leaderId = leaderId;
        this.mannerPointSum = mannerPointSum;
        this.matchCount = matchCount;
        this.region = region;
        this.confirmedMatchIds = confirmedMatchIds;
        this.gender = gender;

    }


    public void confirmMatch(Long matchId) {
        confirmedMatchIds.add(matchId);
    }

    public void cancelMatch(Long matchId) {
        confirmedMatchIds.remove(matchId);
    }

    public void rateMannerPoint(Long point) {
        mannerPointSum += point;
        matchCount++;
    }

    public BigDecimal getMannerPoint() {

        return BigDecimal.valueOf(mannerPointSum).divide(BigDecimal.valueOf(matchCount),2,  RoundingMode.HALF_UP);
    }

}
