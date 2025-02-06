package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.enums.Gender;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamProfileUpdateRequest {

	@NotNull
	private Long id;
	private String teamName;
	private String teamDescription;
	private String teamLogoUrl;
	private String region;
	private Gender gender;

}
