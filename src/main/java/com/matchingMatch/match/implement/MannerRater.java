package com.matchingMatch.match.implement;

import org.springframework.stereotype.Component;

import com.matchingMatch.match.domain.MannerRate;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.exception.MatchAlreadyRatedException;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
import com.matchingMatch.team.domain.Team;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MannerRater {

	private final TeamAdapter teamAdapter;

	public void checkAlreadyRate(Team team, Match match) {
		if (match.getIsHostRate() && match.getHostId().equals(team.getId())) {
			throw new MatchAlreadyRatedException(team.getLeaderId());
		}
		if (match.getIsHostRate() && match.getParticipantId().equals(team.getId())) {
			throw new MatchAlreadyRatedException(team.getLeaderId());
		}
	}

	public void rateMannerPoint(Team team, Match match, MannerRate mannerRate) {
		checkAlreadyRate(team, match);
		if (team.getId().equals(match.getParticipantId())) {
			match.rateParticipantRate();
		} else if (team.getId().equals(match.getHostId())) {
			match.rateHost();
		} else {
			throw new UnauthorizedAccessException();
		}
		team.rateMannerPoint(mannerRate.rate());
	}

	public void checkMatchEnd(Match match) {
		if (!match.isEnd()) {
			throw new IllegalArgumentException("매치가 종료되지 않았습니다.");
		}

	}
}
