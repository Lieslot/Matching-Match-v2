package com.matchingMatch.match.dto;

import java.time.LocalDateTime;

import com.matchingMatch.match.domain.Stadium;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.match.domain.enums.Gender;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostMatchPostRequest {

	@NotNull
	private Long hostId;

	private Long postId;

	@NotNull(message = "시작 시간을 선택해주세요")
	private LocalDateTime startTime;

	@NotNull(message = "종료 시간을 선택해주세요")
	private LocalDateTime endTime;

	@NotNull(message = "성별을 선택해주세요")
	private Gender gender;

	@NotNull(message = "경기장을 선택해주세요")
	private Stadium stadium;

	private int stadiumCost;

	private String etc;

	public MatchEntity toEntity() {
		return MatchEntity.builder()
			.hostId(hostId)
			.startTime(startTime)
			.endTime(endTime)
			.gender(gender)
			.stadiumCost(stadiumCost)
			.etc(etc)
			.build();
	}

}
