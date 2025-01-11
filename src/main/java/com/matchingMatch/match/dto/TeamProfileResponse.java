package com.matchingMatch.match.dto;


import com.matchingMatch.match.domain.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TeamProfileResponse {
    @NotNull
    private String name;
    @NotNull
    private String logoUrl;
    @NotNull
    private BigDecimal mannerPoint;
    @NotNull
    private String description;
    @NotNull
    private String region;
    @NotNull
    private Gender gender;

    @Builder
    public TeamProfileResponse(String teamName, String teamLogoUrl, BigDecimal mannerPoint, String teamDescription,
                               String region, Gender gender) {
        this.name = teamName;
        this.logoUrl = teamLogoUrl;
        this.mannerPoint = mannerPoint;
        this.description = teamDescription;
        this.region = region;
        this.gender = gender;
    }


}
