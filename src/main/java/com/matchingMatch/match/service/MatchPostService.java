package com.matchingMatch.match.service;

import com.matchingMatch.listener.event.MatchDeleteEvent;
import com.matchingMatch.match.MatchAdapter;
import com.matchingMatch.match.MatchTeamValidator;
import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.match.dto.MatchPostListElementResponse;
import com.matchingMatch.match.dto.ModifyMatchPostRequest;
import com.matchingMatch.match.dto.PostMatchPostRequest;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.team.domain.entity.TeamEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchPostService {


    private static final Integer PAGE_SIZE = 20;

    private final MatchAdapter matchAdapter;
    private final MatchTeamValidator matchTeamValidator;
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
        return matchAdapter.getMatchBy(matchId);
    }

    @Transactional
    public void deleteMatchPost(Long matchId, Long userId) {
        Match match = matchAdapter.getMatchBy(matchId);
        matchTeamValidator.checkHost(match, userId);
        matchAdapter.deleteById(matchId);

        eventPublisher.publishEvent(new MatchDeleteEvent(matchId));
    }

    // TODO 추후에 일관성 고민해보기
    @Transactional
    public void updateMatch(ModifyMatchPostRequest updatedMatchPost, Long userId) {

        Match match = matchAdapter.getMatchBy(updatedMatchPost.getPostId());

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
}
