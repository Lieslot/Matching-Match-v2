package com.matchingMatch.match;

import com.matchingMatch.match.domain.MannerRate;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.exception.MatchAlreadyRatedException;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
import com.matchingMatch.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MannerRater {

    private final TeamAdapter teamAdapter;

//    public void checkAlreadyRate(Match2 match, Long userId) {
//
//        Team team = teamAdapter.getTeamByLeaderId(userId);
//
//        if (match.isHostRate() && match.getHostId().equals(team.getId())) {
//            throw new MatchAlreadyRatedException(userId);
//        }
//        if (match.isParticipantRate() && match.getParticipantId().equals(team.getId())) {
//            throw new MatchAlreadyRatedException(userId);
//        }
//    }

    public void checkAlreadyRate(Team team, Match match) {
        if (match.getIsHostRate() && match.getHostId().equals(team.getId())) {
            throw new MatchAlreadyRatedException(team.getLeaderId());
        }
        if (match.getIsHostRate() && match.getParticipantId().equals(team.getId())) {
            throw new MatchAlreadyRatedException(team.getLeaderId());
        }
    }


//    public void rateMannerPoint(Match2 match, MannerRate mannerRate) {
//
//        Team host = teamAdapter.getTeamBy(match.getHostId());
//        Team participant = teamAdapter.getTeamBy(match.getParticipantId());
//        if (mannerRate.isRater(participant.getLeaderId())) {
//            match.rateParticipantRate();
//            host.rateMannerPoint(mannerRate.rate());
//        } else if (mannerRate.isRater(host.getLeaderId())) {
//            match.rateHost();
//            participant.rateMannerPoint(mannerRate.rate());
//        } else {
//            throw new UnauthorizedAccessException();
//        }
//
//    }

    public void rateMannerPoint(Team team, Match match2, MannerRate mannerRate) {
        checkAlreadyRate(team, match2);
        if (team.getId().equals(match2.getParticipantId())) {
            match2.rateParticipantRate();
        } else if (team.getId().equals(match2.getHostId())) {
            match2.rateHost();
        } else {
            throw new UnauthorizedAccessException();
        }
        team.rateMannerPoint(mannerRate.rate());
    }


}
