package com.matchingMatch.listener.event;

import lombok.Getter;

@Getter
public class MatchCancelEvent {
	private Long sendTeamId;
	private Long targetTeamId;
	private Long matchId;

	public MatchCancelEvent(Long matchId, Long targetTeamId, Long sendTeamId) {
		this.targetTeamId = targetTeamId;
		this.sendTeamId = sendTeamId;
		this.matchId = matchId;
	}
}
