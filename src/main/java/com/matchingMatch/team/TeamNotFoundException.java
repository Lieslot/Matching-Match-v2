package com.matchingMatch.team;

public class TeamNotFoundException extends RuntimeException {

	public static final String ERROR_MESSAGE = "Team Not Found";

	public TeamNotFoundException(Long teamId) {
		super("Team Not Found" + "teamId: " + teamId);
	}
}
