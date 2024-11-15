package com.matchingMatch.match.service;


import com.matchingMatch.match.dto.ModifyMatchPostRequest;
import com.matchingMatch.match.MatchAdapter;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import com.matchingMatch.match.domain.repository.MatchRequestRepository;
import com.matchingMatch.match.dto.PostMatchPostRequest;
import com.matchingMatch.team.domain.entity.TeamEntity;
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
    private final MatchAdapter matchAdapter;
    private final ApplicationEventPublisher eventPublisher;
    private final MatchRequestRepository matchRequestRepository;

    public Long postNewMatch(PostMatchPostRequest match, Long userId) {
        TeamEntity host = teamRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedAccessException());

        MatchEntity matchPost = match.toEntity();

        matchPost.setHost(host.getId());
        matchPost.setParticipant(host.getId());

        Long newId = matchRepository.save(matchPost).getId();

        return newId;
    }

    public List<Match> getPosts() {
        return matchAdapter.getMatches();
    }

    public Match getMatch(Long matchId) {
        return matchAdapter.getMatchBy(matchId);
    }

    public void deleteMatchPost(Long matchId, Long userId) {
        Match match = matchAdapter.getMatchBy(matchId);
        match.checkHostEqualTo(userId);
        matchRepository.deleteById(matchId);
    }

    // TODO 추후에 일관성 고민해보기
    @Transactional
    public void updateMatch(ModifyMatchPostRequest updatedMatchPost , Long userId) {
        MatchEntity matchEntity = matchRepository.findById(
                updatedMatchPost.getPostId()
        ).orElseThrow(() -> new MatchNotFoundException());

        matchEntity.isHost(userId);

        matchRepository.save(matchEntity);
    }

    public List<Match> getMyMatches (Long teamId) {
        // matchRequestEntity에서 userId와 같은 teamId matchId를 가져온다.

        List<MatchRequestEntity> myMatchRequests = matchRequestRepository.findBySendTeamId(teamId);

        return myMatchRequests.stream()
                .map(matchRequest -> matchAdapter.getMatchBy(matchRequest.getMatchId()))
                .toList();
    }

    public List<Match> getOtherMatches (Long teamId) {
        // matchRequestEntity에서 userId와 같은 teamId matchId를 가져온다.

        List<MatchRequestEntity> otherMatchRequests = matchRequestRepository.findByTargetTeamId(teamId);

        return otherMatchRequests.stream()
                .map(matchRequest -> matchAdapter.getMatchBy(matchRequest.getMatchId()))
                .toList();
    }

    @Transactional
    public void sendMatchRequest(Long matchId, Long requestTeamId) {

        MatchEntity match = matchRepository.findById(matchId).orElseThrow(() -> new MatchNotFoundException());

        boolean teamExists = teamRepository.existsById(requestTeamId);
        if (!teamExists) {
            throw new IllegalArgumentException();
        }

        MatchRequestEntity matchRequest = MatchRequestEntity.builder()
                .teamId(requestTeamId)
                .matchId(matchId)
                .targetTeamId(match.getHostId())
                .build();

        matchRequestRepository.save(matchRequest);
        // TODO: 알림 보내기
    }

    @Transactional
    public void cancelMatchRequest(Long requestId, Long userId) {

        MatchRequestEntity matchRequest = matchRequestRepository.findById(requestId)
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

//    public List<MatchEntity> getPagedMatchPostsByNoOffset(Long lastMatchId, Integer pageSize) {
//
//    }
}
