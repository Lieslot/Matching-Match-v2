package com.matchingMatch.match.dto;

import java.time.LocalDateTime;

import com.matchingMatch.match.domain.enums.Gender;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ModifyMatchPostRequest {

	@NotNull
	private Long postId;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Gender gender;

	private Integer stadiumCost;

	private String etc;

}
