package com.matchingMatch.match.service;


import com.matchingMatch.listener.event.MatchDeleteEvent;
import com.matchingMatch.match.MannerRater;
import com.matchingMatch.match.MatchRequestAdapter;
import com.matchingMatch.match.MatchTeamValidator;
import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.match.domain.MannerRate;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.dto.MatchPostListElementResponse;
import com.matchingMatch.match.dto.ModifyMatchPostRequest;
import com.matchingMatch.match.MatchAdapter;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import com.matchingMatch.match.dto.PostMatchPostRequest;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.team.domain.entity.TeamEntity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private static final Integer PAGE_SIZE = 20;

    private final MatchAdapter matchAdapter;
    private final MatchRequestAdapter matchRequestAdapter;
    private final MatchTeamValidator matchTeamValidator;
    private final MannerRater mannerRater;
    private final ApplicationEventPublisher eventPublisher;

    private final TeamAdapter teamAdapter;

    public Long postNewMatch(PostMatchPostRequest match, Long userId) {
        TeamEntity host = teamAdapter.getTeamEntityBy(match.getHostId());

        MatchEntity matchPost = match.toEntity();

        matchPost.setHost(host.getId());
        matchPost.setStadiumId(match.getStadium().getId());

        Long newId = matchAdapter.save(matchPost);

        return newId;
    }


    public List<MatchPostListElementResponse> getPosts() {
        List<Match> matches = matchAdapter.getMatches();

        return matches.stream()
                .map((match) -> {
                    Team host = teamAdapter.getTeamBy(match.getHostId());
                    Team participant = teamAdapter.getTeamBy(match.getParticipantId());
                    return MatchPostListElementResponse.of(
                            match.getId(),
                            host.getName(),
                            participant.getName(),
                            match.getStartTime(),
                            match.getEndTime()
                    );
                } )
                .toList();
    }

    public Match getMatch(Long matchId) {
        return matchAdapter.getMatchBy2(matchId);
    }

    @Transactional
    public void deleteMatchPost(Long matchId, Long userId) {
        Match match = matchAdapter.getMatchBy2(matchId);
        matchTeamValidator.checkHost(match, userId);
        matchAdapter.deleteById(matchId);

        eventPublisher.publishEvent(new MatchDeleteEvent(matchId));
    }

    // TODO 추후에 일관성 고민해보기
    @Transactional
    public void updateMatch(ModifyMatchPostRequest updatedMatchPost, Long userId) {

        Match match = matchAdapter.getMatchBy2(updatedMatchPost.getPostId());

        matchTeamValidator.checkHost(match, userId);
        match.update(updatedMatchPost);

        matchAdapter.updateMatch(match);
    }


    public List<MatchPostListElementResponse> getMyMatches (Long userId) {
        // matchRequestEntity에서 userId와 같은 teamId matchId를 가져온다.
        Team userTeam = teamAdapter.getTeamByLeaderId(userId);
        List<Match> matches = matchAdapter.getTeamRequestedMatches(userTeam.getId());
        return matches.stream()
                .map((match) -> {
                    Team host = teamAdapter.getTeamBy(match.getHostId());
                    Team participant = teamAdapter.getTeamBy(match.getParticipantId());
                    return MatchPostListElementResponse.of(
                            match.getId(),
                            host.getName(),
                            participant.getName(),
                            match.getStartTime(),
                            match.getEndTime()
                    );
                } )
                .toList();
    }

    public List<MatchPostListElementResponse>  getOtherMatches (Long userId) {
        // matchRequestEntity에서 userId와 같은 teamId matchId를 가져온다.
        Team userTeam = teamAdapter.getTeamByLeaderId(userId);
        List<Match> matches = matchAdapter.getTeamRequestingMatches(userTeam.getId());

        return matches.stream()
                .map((match) -> {
                    Team host = teamAdapter.getTeamBy(match.getHostId());
                    Team participant = teamAdapter.getTeamBy(match.getParticipantId());
                    return MatchPostListElementResponse.of(
                            match.getId(),
                            host.getName(),
                            participant.getName(),
                            match.getStartTime(),
                            match.getEndTime()
                    );
                } )
                .toList();
    }


    public List<MatchPostListElementResponse> getHostingMatches(Long teamId) {
        // TODO refactor
        Team team = teamAdapter.getTeamBy(teamId);
        List<Match> matches = matchAdapter.getHostingMatches(teamId);

        return matches.stream()
                .map((match) -> {
                    Team host = teamAdapter.getTeamBy(match.getHostId());
                    Team participant = teamAdapter.getTeamBy(match.getParticipantId());
                    return MatchPostListElementResponse.of(
                            match.getId(),
                            host.getName(),
                            participant.getName(),
                            match.getStartTime(),
                            match.getEndTime()
                    );
                } )
                .toList();
    }


    @Transactional
    public void sendMatchRequest(Long matchId, Long userId) {

        // TODO 이미 확정된 매치가 일정이 겹치는 경우 예외처리 추가

        Match match = matchAdapter.getMatchBy2(matchId);
        Team sendTeam = teamAdapter.getTeamByLeaderId(userId);

        match.checkAlreadyConfirmed();

        boolean teamExists = teamAdapter.existsById(userId);
        if (!teamExists) {
            throw new IllegalArgumentException();
        }

        MatchRequestEntity matchRequest = MatchRequestEntity.builder()
                .sendTeamId(sendTeam.getId())
                .matchId(matchId)
                .targetTeamId(match.getHostId())
                .build();
        matchRequestAdapter.save(matchRequest);

        // TODO: 매치 요청 알림 보내기
    }

    @Transactional
    public void cancelMatchRequest(Long requestId, Long userId) {

        MatchRequestEntity matchRequest = matchRequestAdapter.findById(requestId);
        Match match = matchAdapter.getMatchBy2(matchRequest.getMatchId());

        match.checkAlreadyConfirmed();
        matchTeamValidator.checkHost(match, userId);

        matchRequestAdapter.deleteById(requestId);
    }

    @Transactional
    public void confirmMatchRequest(Long matchId, Long currentUserId, Long requestingTeamId) {

        Match match = matchAdapter.getMatchBy2(matchId);
        Team requestingTeam = teamAdapter.getTeamBy(requestingTeamId);


        matchTeamValidator.checkHost(match, currentUserId);
        match.checkAlreadyConfirmed();
        match.confirmMatch(requestingTeam);

        matchAdapter.updateMatch(match);
        matchRequestAdapter.deleteAllByMatchId(matchId);

        // TODO 알림 보내기
    }

    @Transactional
    public void cancelConfirmedMatch(Long matchId, Long currentUserId) {

        Match match = matchAdapter.getMatchBy2(matchId);

        matchTeamValidator.checkHostOrParticipant(match, currentUserId);
        match.checkCancelDeadline(LocalDateTime.now());
        match.cancelMatch();

        matchAdapter.updateMatch(match);
    }

    @Transactional
    public void refuseMatchRequest(Long matchRequestId, Long currentUserId) {

        MatchRequestEntity request = matchRequestAdapter.findById(matchRequestId);
        Match match = matchAdapter.getMatchBy2(request.getMatchId());

        matchTeamValidator.checkHostOrParticipant(match, currentUserId);

        match.checkAlreadyConfirmed();

        matchRequestAdapter.deleteByMatchIdAndSendTeamId(matchRequestId, match.getHostId());

    }

    @Transactional
    public void rateMannerPoint(Long matchId, MannerRate mannerRate) {

        Match match = matchAdapter.getMatchBy2(matchId);
        Team team = teamAdapter.getTeamByLeaderId(mannerRate.userId());

        matchTeamValidator.checkHostOrParticipant(match, team);
        mannerRater.checkAlreadyRate(team, match);
        mannerRater.rateMannerPoint(team, match, mannerRate);

        matchAdapter.updateMatch(match);
        matchAdapter.updateMannerRateCheck(match);
        teamAdapter.save(team);

    }

//    public List<MatchEntity> getPagedMatchPostsByNoOffset(Long lastMatchId, Integer pageSize) {
//
//    }
}
