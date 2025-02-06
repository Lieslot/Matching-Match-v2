package com.matchingMatch.match.implement;

import org.springframework.stereotype.Component;

import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
import com.matchingMatch.team.domain.entity.Team;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MatchTeamValidator {

	private final TeamAdapter teamAdapter;

	public void checkHost(Match match, Long userId) {

		Team host = teamAdapter.getTeamBy(match.getHostId());
		if (!host.getLeaderId().equals(userId)) {
			throw new UnauthorizedAccessException();
		}
	}

	public void checkHostOrParticipant(Match match, Long userId) {
		Team host = teamAdapter.getTeamBy(match.getHostId());
		Team participant = teamAdapter.getTeamBy(match.getParticipantId());
		if (!host.getLeaderId().equals(userId) && !participant.getLeaderId().equals(userId)) {
			throw new UnauthorizedAccessException();
		}
	}

	public void checkHostOrParticipant(Match match2, Team team) {
		if (!match2.getHostId().equals(team.getId()) && !match2.getParticipantId().equals(team.getId())) {
			throw new UnauthorizedAccessException();
		}
	}

}
