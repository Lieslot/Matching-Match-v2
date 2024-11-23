package com.matchingMatch.team.dto;

import com.matchingMatch.match.domain.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TeamRegisterRequest {

    @NotNull
    private String name;
    private String logoUrl;
    private String description;
    @NotNull
    private Gender gender;
    private String region;

}

