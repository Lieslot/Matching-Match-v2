package com.matchingMatch.match.service;


import com.matchingMatch.match.domain.MannerRate;
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
                .orElseThrow(UnauthorizedAccessException::new);

        MatchEntity matchPost = match.toEntity();

        matchPost.setHost(host.getId());
        matchPost.setStadiumId(match.getStadium().getId());

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
        match.checkHost(userId);
        matchRepository.deleteById(matchId);
    }

    // TODO 추후에 일관성 고민해보기
    @Transactional
    public void updateMatch(ModifyMatchPostRequest updatedMatchPost , Long userId) {
        MatchEntity matchEntity = matchRepository.findById(
                updatedMatchPost.getPostId()
        ).orElseThrow(MatchNotFoundException::new);

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

        // TODO 이미 확정된 매치가 일정이 겹치는 경우 예외처리 추가

        Match match = matchAdapter.getMatchBy(matchId);

        match.checkAlreadyConfirmed();

        boolean teamExists = teamRepository.existsById(requestTeamId);
        if (!teamExists) {
            throw new IllegalArgumentException();
        }

        MatchRequestEntity matchRequest = MatchRequestEntity.builder()
                .teamId(requestTeamId)
                .matchId(matchId)
                .targetTeamId(match.getHost().getId())
                .build();

        matchRequestRepository.save(matchRequest);
        // TODO: 알림 보내기
    }

    @Transactional
    public void cancelMatchRequest(Long requestId, Long userId) {

        List<TeamEntity> userTeams = teamRepository.findAllByLeaderId(userId);

        MatchRequestEntity matchRequest = matchRequestRepository.findById(requestId)
                .orElseThrow(IllegalArgumentException::new);

        matchRequest.checkCancelDeadline();

        userTeams.stream()
                .filter(team -> matchRequest.hasSendTeam(team.getId()))
                .findFirst()
                .orElseThrow(UnauthorizedAccessException::new);

        matchRequestRepository.deleteById(requestId);
    }

    @Transactional
    public void confirmMatchRequest(Long matchId, Long currentUserId, Long requestingTeamId) {

        Match match = matchAdapter.getMatchBy(matchId);

        match.checkHost(currentUserId);
        match.checkAlreadyConfirmed();

        TeamEntity teamEntity = teamRepository.findById(requestingTeamId).orElseThrow(IllegalArgumentException::new);

        match.confirmParticipant(teamEntity.toDomain());

        matchAdapter.updateMatch(match);
        matchRequestRepository.deleteAllByMatchId(matchId);

        // TODO 알림 보내기
    }

    @Transactional
    public void cancelConfirmedMatch(Long matchId, Long currentUserId) {

        Match match = matchAdapter.getMatchBy(matchId);

        match.checkHostOrParticipant(currentUserId);
        match.cancelParticipant();

        matchAdapter.updateMatch(match);
    }

    @Transactional
    public void refuseMatchRequest(Long matchId, Long currentUserId, Long requestingTeamId) {

        Match match = matchAdapter.getMatchBy(matchId);

        match.checkHost(currentUserId);
        match.checkAlreadyConfirmed();

        matchRequestRepository.deleteByMatchIdAndSendTeamId(matchId, requestingTeamId);

        // TODO 알림 보내기
    }

    @Transactional
    public void rateMannerPoint(Long matchId, MannerRate mannerRate) {

        Match match = matchAdapter.getMatchWithRateCheck(matchId);

        match.checkHostOrParticipant(mannerRate.userId());
        match.rateMannerPoint(mannerRate);

        matchAdapter.updateMatch(match);

    }

//    public List<MatchEntity> getPagedMatchPostsByNoOffset(Long lastMatchId, Integer pageSize) {
//
//    }
}
