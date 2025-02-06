package com.matchingMatch.listener.event;

import lombok.Getter;

@Getter
public class MannerRateEvent {

	private Long matchId;
	private Long targetTeamId;
	private Long sendTeamId;

	public MannerRateEvent(Long matchId, Long targetTeamId, Long sendTeamId) {
		this.targetTeamId = targetTeamId;
		this.sendTeamId = sendTeamId;
		this.matchId = matchId;
	}
}
