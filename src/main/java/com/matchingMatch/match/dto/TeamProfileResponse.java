package com.matchingMatch.match.dto;


import com.matchingMatch.match.domain.enums.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TeamProfileResponse {

    private String teamName;
    private String teamLogoUrl;
    private Float mannerPoint;
    private String teamDescription;
    private String region;
    private Gender gender;

    @Builder
    public TeamProfileResponse(String teamName, String teamLogoUrl, Float mannerPoint, String teamDescription,
                               String region, Gender gender) {
        this.teamName = teamName;
        this.teamLogoUrl = teamLogoUrl;
        this.mannerPoint = mannerPoint;
        this.teamDescription = teamDescription;
        this.region = region;
        this.gender = gender;
    }


}
