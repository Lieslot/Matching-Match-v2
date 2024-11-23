package com.matchingMatch.match.dto;


import com.matchingMatch.match.domain.enums.Gender;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TeamProfileResponse {

    private String name;
    private String logoUrl;
    private BigDecimal mannerPoint;
    private String description;
    private String region;
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
