package com.matchingMatch.team.dto;

import com.matchingMatch.match.domain.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TeamRegisterRequest {

    @NotNull
    private String name;
    private String logoUrl;
    @NotNull
    private String description;
    @NotNull
    private Gender gender;
    @NotNull
    private String region;

}

