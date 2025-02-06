package com.matchingMatch.listener.event;

import lombok.Getter;

@Getter
public class MatchRequestEvent {
	private Long sendTeamId;
	private Long targetTeamId;
	private Long matchId;

	public MatchRequestEvent(Long matchId, Long targetTeamId, Long sendTeamId) {
		this.targetTeamId = targetTeamId;
		this.sendTeamId = sendTeamId;
		this.matchId = matchId;
	}
}
