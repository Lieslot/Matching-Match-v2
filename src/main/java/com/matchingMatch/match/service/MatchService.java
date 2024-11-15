package com.matchingMatch.match.service;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.MatchRequest;
import com.matchingMatch.match.domain.repository.MatchRequestRepository;
import com.matchingMatch.match.dto.PostMatchPostRequest;
import com.matchingMatch.team.domain.Team;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.exception.MatchNotFoundException;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class MatchService {

    private static final String INVALID_AUTHORITY = "권한이 없는 접근입니다.";
    private static final Integer PAGE_SIZE = 20;

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final MatchRequestRepository matchRequestRepository;

    public Long postNewMatch(PostMatchPostRequest match, Long userId) {
        Team host = teamRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedAccessException());
        Match matchPost = match.toEntity();

        matchPost.setHost(host.getId());
        matchPost.setParticipant(host.getId());

        Long newId = matchRepository.save(matchPost).getId();

        return newId;
    }

    public Match getMatchPostBy(Long matchId) {
        return matchRepository.findById(matchId).orElse(null);
    }

    public void cancelMatch(Long matchId, Long userId) {
        Match matchPost = getMatchPostBy(matchId);
        matchPost.checkHostEqualTo(userId);
        matchRepository.deleteById(matchId);
    }

    public void updateMatch(PostMatchPostRequest updatedMatchPost , Long userId) {
        boolean isMatchExists = matchRepository.existsById(updatedMatchPost.getPostId());

        if (!isMatchExists) {
            throw new MatchNotFoundException();
        }

        Match match = updatedMatchPost.toEntity();
        match.isHost(userId);

        matchRepository.save(match);
    }

    @Transactional
    public void sendMatchRequest(Long matchId, Long requestTeamId) {

        boolean matchExists = matchRepository.existsById(matchId);
        if (!matchExists) {
            throw new MatchNotFoundException();
        }
        boolean teamExists = teamRepository.existsById(requestTeamId);
        if (!teamExists) {
            throw new UnauthorizedAccessException();
        }

        MatchRequest matchRequest = new MatchRequest(requestTeamId, matchId);
        matchRequestRepository.save(matchRequest);

        // TODO: 알림 보내기
    }

    @Transactional
    public void cancelMatchRequest(Long requestId, Long userId) {

        MatchRequest matchRequest = matchRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException());


        // TODO 알림 보내기


        matchRequestRepository.deleteById(requestId);
    }

    @Transactional
    public void confirmMatchRequest(Long matchId, Long currentUserId, Long confirmedTeamId) {



    }

    @Transactional
    public void cancelConfirmedMatch(Long matchId, Long currentUserId) {

    }

    public void rateMannerPoint(Long matchId, Long currentUserId, Long mannerPoint) {

    }

    public List<Match> getPagedMatchPostsByNoOffset(Long lastMatchId, Integer pageSize) {

    }
}
