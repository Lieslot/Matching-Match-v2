package com.matchingMatch.match.service;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.MatchRequest;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.dto.MatchCancelEvent;
import com.matchingMatch.match.dto.MatchConfirmEvent;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private static final String INVALID_AUTHORITY = "권한이 없는 접근입니다.";
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Long save(Match match, Long userId) {

        Optional<Team> result = teamRepository.findById(userId);

        if (result.isEmpty()) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

        Team host = result.get();

        match.setHost(host);
        Match newMatch = matchRepository.save(match);

        return newMatch.getId();
    }

    public Match getMatchPostBy(Long matchId) {

        Optional<Match> result = matchRepository.findById(matchId);
        return result.orElse(null);

    }

    public void deleteMatchPostBy(Long matchId, Long userId) {
        Match matchPost = getMatchPostBy(matchId);

        checkHostUser(matchPost.getHost(), userId);

        matchRepository.deleteById(matchId);

    }

    public void updateMatch(Match updatedMatchPost, Long userId) {

        checkHostUser(updatedMatchPost.getHost(), userId);

        boolean isExists = matchRepository.existsById(updatedMatchPost.getId());

        if (!isExists) {
            throw new IllegalArgumentException();
        }

        matchRepository.save(updatedMatchPost);

    }

    private void checkHostUser(Team hostTeam, Long currentUserId) {
        if (!currentUserId.equals(hostTeam.getId())) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }
    }

    @Transactional
    public void addMatchRequest(Long matchId, Long requestTeamId) {

        Optional<Match> matchResult = matchRepository.findById(matchId);
        Optional<Team> teamResult = teamRepository.findById(requestTeamId);

        if (teamResult.isEmpty() || matchResult.isEmpty()) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }
        Team team = teamResult.get();
        Match match = matchResult.get();

        MatchRequest matchRequest = new MatchRequest(team, match);
        match.addRequestTeam(matchRequest);



    }

    @Transactional
    public void cancelMatchRequest(Long matchId, Long teamId) {

        Optional<Match> matchResult = matchRepository.findById(matchId);
        Optional<Team> teamResult = teamRepository.findById(teamId);

        if (teamResult.isEmpty() || matchResult.isEmpty()) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }
        Team team = teamResult.get();
        Match match = matchResult.get();

        MatchRequest matchRequest = new MatchRequest(team, match);
        match.deleteRequestTeam(matchRequest);

    }

    @Transactional
    public void confirmMatchRequest(Long matchId, Long currentUserId, Long confirmedTeamId) {

        Optional<Match> matchResult = matchRepository.findById(matchId);
        Optional<Team> teamResult = teamRepository.findById(confirmedTeamId);

        if (teamResult.isEmpty() || matchResult.isEmpty()) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

        Team participantTeam = teamResult.get();
        Match match = matchResult.get();

        checkHostUser(match.getHost(), currentUserId);

        eventPublisher.publishEvent(new MatchConfirmEvent(participantTeam, match));

        match.setParticipant(participantTeam);

        participantTeam.confirmParticipant(match);
    }

    @Transactional
    public void cancelConfirmedMatch(Long matchId, Long currentUserId) {

        Optional<Match> matchResult = matchRepository.findById(matchId);
        Optional<Team> teamResult = teamRepository.findById(currentUserId);

        if (teamResult.isEmpty() || matchResult.isEmpty()) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

        Match match = matchResult.get();
        Team team = teamResult.get();

        if (!match.getParticipant()
                  .equals(team) && !match.getHost()
                                         .equals(team)) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

        eventPublisher.publishEvent(new MatchCancelEvent(team, match));
        match.setParticipant(null);
        team.cancelParticipant(match);
    }


    public void rateMannerPoint(Long matchId, Long currentUserId, Long mannerPoint) {

        Optional<Match> matchResult = matchRepository.findById(matchId);
        Optional<Team> teamResult = teamRepository.findById(currentUserId);

        if (matchResult.isEmpty() || teamResult.isEmpty()) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

        Match match = matchResult.get();
        Team team = teamResult.get();



        if (match.getParticipant().equals(team)) {
            match.getMatchRateCheck().checkHost();
            match.getHost().isRatedAfterMatch(mannerPoint);
        } else if (match.getHost().equals(team)) {
            match.getMatchRateCheck().checkParticipant();
            match.getParticipant().isRatedAfterMatch(mannerPoint);
        } else {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }


    }
}
