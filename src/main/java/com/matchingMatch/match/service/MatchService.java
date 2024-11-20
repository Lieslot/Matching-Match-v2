package com.matchingMatch.match.service;


import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.match.domain.MannerRate;
import com.matchingMatch.match.dto.ModifyMatchPostRequest;
import com.matchingMatch.match.MatchAdapter;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import com.matchingMatch.match.domain.repository.MatchRequestRepository;
import com.matchingMatch.match.dto.PostMatchPostRequest;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.team.domain.entity.TeamEntity;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private static final Integer PAGE_SIZE = 20;

    private final MatchAdapter matchAdapter;
    private final MatchRequestAdapter matchRequestAdapter;
//    private final ApplicationEventPublisher eventPublisher;

    private final TeamAdapter teamAdapter;

    public Long postNewMatch(PostMatchPostRequest match, Long userId) {
        TeamEntity host = teamAdapter.getTeamEntityBy(match.getHostId());

        // TODO 유저가 team을 소유하는지 확인하는 예외처리

        MatchEntity matchPost = match.toEntity();

        matchPost.setHost(host.getId());
        matchPost.setStadiumId(match.getStadium().getId());

        Long newId = matchAdapter.save(matchPost);

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
        matchAdapter.deleteById(matchId);
    }

    // TODO 추후에 일관성 고민해보기
    @Transactional
    public void updateMatch(ModifyMatchPostRequest updatedMatchPost , Long userId) {

        Match match = matchAdapter.getMatchBy(updatedMatchPost.getPostId());

        match.checkHost(userId);
        match.update(updatedMatchPost);

        matchAdapter.saveMatch(match);
    }


    public List<Match> getMyMatches (Long teamId) {
        // matchRequestEntity에서 userId와 같은 teamId matchId를 가져온다.
        return matchAdapter.getMyMatches(teamId);
    }

    public List<Match> getOtherMatches (Long teamId) {
        // matchRequestEntity에서 userId와 같은 teamId matchId를 가져온다.
        return matchAdapter.getOtherMatches(teamId);
    }

    @Transactional
    public void sendMatchRequest(Long matchId, Long requestTeamId) {

        // TODO 이미 확정된 매치가 일정이 겹치는 경우 예외처리 추가

        Match match = matchAdapter.getMatchBy(matchId);

        match.checkAlreadyConfirmed();

        boolean teamExists = teamAdapter.existsById(requestTeamId);
        if (!teamExists) {
            throw new IllegalArgumentException();
        }

        MatchRequestEntity matchRequest = MatchRequestEntity.builder()
                .teamId(requestTeamId)
                .matchId(matchId)
                .targetTeamId(match.getHost().getId())
                .build();

        matchRequestAdapter.save(matchRequest);
        // TODO: 알림 보내기
    }

    @Transactional
    public void cancelMatchRequest(Long requestId, Long userId) {

        List<Team> userTeams = teamAdapter.getUserTeams(userId);

        MatchRequestEntity matchRequest = matchRequestAdapter.findById(requestId);

        matchRequest.checkCancelDeadline();

        userTeams.stream()
                .filter(team -> matchRequest.hasSendTeam(team.getId()))
                .findFirst()
                .orElseThrow(UnauthorizedAccessException::new);

        matchRequestAdapter.deleteById(requestId);
    }

    @Transactional
    public void confirmMatchRequest(Long matchId, Long currentUserId, Long requestingTeamId) {

        Match match = matchAdapter.getMatchBy(matchId);

        match.checkHost(currentUserId);
        match.checkAlreadyConfirmed();

        Team teamEntity = teamAdapter.getTeamBy(requestingTeamId);

        match.confirmMatch(teamEntity);

        matchAdapter.updateMatch(match);
        matchRequestAdapter.deleteAllByMatchId(matchId);

        // TODO 알림 보내기
    }

    @Transactional
    public void cancelConfirmedMatch(Long matchId, Long currentUserId) {

        Match match = matchAdapter.getMatchBy(matchId);

        match.checkHostOrParticipant(currentUserId);
        match.cancelMatch();

        matchAdapter.updateMatch(match);
    }

    @Transactional
    public void refuseMatchRequest(Long matchId, Long currentUserId, Long requestingTeamId) {

        Match match = matchAdapter.getMatchBy(matchId);

        match.checkHost(currentUserId);
        match.checkAlreadyConfirmed();

        matchRequestAdapter.deleteByMatchIdAndSendTeamId(matchId, requestingTeamId);

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
