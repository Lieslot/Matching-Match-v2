package com.matchingMatch.match.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MatchRequestDetail {

	private Long targetTeamId;
	private Long matchId;
	private Long stadiumId;
	private String targetTeamName;
	private String startTime;
	private String endTime;
	private String stadiumName;
	private String stadiumAddress;

	@Builder
	public MatchRequestDetail(Long targetTeamId, Long matchId, String targetTeamName, String startTime, String endTime,
		String stadiumName, String stadiumAddress) {
		this.targetTeamId = targetTeamId;
		this.matchId = matchId;
		this.targetTeamName = targetTeamName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.stadiumName = stadiumName;
		this.stadiumAddress = stadiumAddress;
	}
}
