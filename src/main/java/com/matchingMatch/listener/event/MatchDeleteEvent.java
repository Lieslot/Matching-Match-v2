package com.matchingMatch.listener.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchDeleteEvent {
	private Long matchId;
}
