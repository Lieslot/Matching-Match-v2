package com.matchingMatch.team.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

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

    @Builder
    public Team(Long id, String name, String teamDescription, String teamLogoUrl, Long leaderId, Long mannerPointSum, Long matchCount, String region) {

        this.id = id;
        this.name = name;
        this.teamDescription = teamDescription;
        this.teamLogoUrl = teamLogoUrl;
        this.leaderId = leaderId;
        this.mannerPointSum = mannerPointSum;
        this.matchCount = matchCount;
        this.region = region;
    }



}
