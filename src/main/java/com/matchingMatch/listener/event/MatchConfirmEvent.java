package com.matchingMatch.listener.event;

import lombok.Getter;

@Getter
public class MatchConfirmEvent {

	private Long sendTeamId;
	private Long targetTeamId;
	private Long matchId;

	public MatchConfirmEvent(Long matchId, Long targetTeamId, Long sendTeamId) {
		this.targetTeamId = targetTeamId;
		this.sendTeamId = sendTeamId;
		this.matchId = matchId;
	}
}
