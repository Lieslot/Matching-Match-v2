package com.matchingMatch.match.service;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.MatchRequest;
import com.matchingMatch.team.domain.Team;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.dto.MatchCancelEvent;
import com.matchingMatch.match.dto.MatchConfirmEvent;
import com.matchingMatch.match.exception.MatchNotFoundException;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class MatchService {

    private static final String INVALID_AUTHORITY = "권한이 없는 접근입니다.";
    private static final Integer PAGE_SIZE = 20;

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Long postNewMatch(Match match, Long userId) {
        Team host = teamRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedAccessException());

        match.setHost(host);
        Match newMatch = matchRepository.save(match);

        return newMatch.getId();
    }

    public Match getMatchPostBy(Long matchId) {
        return matchRepository.findById(matchId).orElse(null);
    }

    public void cancelMatch(Long matchId, Long userId) {
        Match matchPost = getMatchPostBy(matchId);
        matchPost.checkHostEqualTo(userId);
        matchRepository.deleteById(matchId);
    }

    public void updateMatch(Match updatedMatchPost, Long userId) {
        updatedMatchPost.checkHostEqualTo(userId);
        boolean isMatchExists = matchRepository.existsById(updatedMatchPost.getId());

        if (!isMatchExists) {
            throw new MatchNotFoundException();
        }

        matchRepository.save(updatedMatchPost);
    }

    @Transactional
    public void sendMatchRequest(Long matchId, Long requestTeamId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException());
        Team team = teamRepository.findById(requestTeamId)
                .orElseThrow(() -> new UnauthorizedAccessException());

        MatchRequest matchRequest = new MatchRequest(team, match);
        match.addRequestTeam(matchRequest);
        matchRepository.save(match);
    }

    @Transactional
    public void cancelMatchRequest(Long matchId, Long teamId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException());
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new UnauthorizedAccessException());

        MatchRequest matchRequest = new MatchRequest(team, match);
        match.removeMatchRequest(matchRequest);
    }

    @Transactional
    public void confirmMatchRequest(Long matchId, Long currentUserId, Long confirmedTeamId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException());
        Team participantTeam = teamRepository.findById(confirmedTeamId)
                .orElseThrow(() -> new UnauthorizedAccessException());

        match.checkHostEqualTo(currentUserId);

        eventPublisher.publishEvent(new MatchConfirmEvent(participantTeam, match));
        match.setParticipant(participantTeam);
        participantTeam.confirmParticipant(match);
    }

    @Transactional
    public void cancelConfirmedMatch(Long matchId, Long currentUserId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException());
        Team team = teamRepository.findById(currentUserId)
                .orElseThrow(() -> new UnauthorizedAccessException());

        match.checkInvolvedInMatch(currentUserId);

        eventPublisher.publishEvent(new MatchCancelEvent(team, match));
        match.setParticipant(null);
        team.cancelParticipant(match);
    }

    public void rateMannerPoint(Long matchId, Long currentUserId, Long mannerPoint) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException());

        match.rateMannerPoint(currentUserId, mannerPoint);
    }

    public List<Match> getPagedMatchPostsByNoOffset(Long lastMatchId, Integer pageSize) {
        return matchRepository.findPagesById(lastMatchId,
                PageRequest.of(0, pageSize));
    }
}
