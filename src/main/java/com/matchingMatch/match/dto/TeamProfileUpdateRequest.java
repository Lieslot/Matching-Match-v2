package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.enums.Gender;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamProfileUpdateRequest {

    @NotNull
    private Long id;
    @NotNull
    private String teamName;
    @NotNull
    private String teamDescription;
    @NotNull
    private String teamLogoUrl;
    @NotNull
    private String region;
    @NotNull
    private Gender gender;

}
