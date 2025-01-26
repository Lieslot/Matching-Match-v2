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
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MatchPostService {


    private static final Integer PAGE_SIZE = 20;

    private final MatchAdapter matchAdapter;
    private final MatchTeamValidator matchTeamValidator;
    private final ApplicationEventPublisher eventPublisher;

    private final TeamAdapter teamAdapter;

    public Long postNewMatch(PostMatchPostRequest match, Long userId) {
        TeamEntity host = teamAdapter.getTeamEntityByLeaderId(userId);

        MatchEntity matchPost = match.toEntity();

        matchPost.setHostId(host.getId());
        matchPost.setStadiumId(match.getStadium().getId());

        return matchAdapter.save(matchPost);
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

    public Match getMatch(Long matchId) {
        return matchAdapter.getMatchBy(matchId);
    }


    public List<MatchPostListElementResponse> getPosts() {
        List<Match> matches = matchAdapter.getCurrentMatches();

        return toResponse(matches);
    }

    public List<MatchPostListElementResponse> getMyMatches (Long userId) {

        Team userTeam = teamAdapter.getTeamByLeaderId(userId);
        List<Match> matches = matchAdapter.getMatchesByHostId(userTeam.getId());
        return toResponse(matches);
    }

    public List<MatchPostListElementResponse>  getOtherMatches (Long userId) {

        Team userTeam = teamAdapter.getTeamByLeaderId(userId);
        List<Match> matches = matchAdapter.getMatchesByParticipantId(userTeam.getId());

        return toResponse(matches);
    }

    public List<MatchPostListElementResponse> getHostingMatches(Long teamId) {
        // TODO refactor
        List<Match> matches = matchAdapter.getMatchesByHostId(teamId);

        return toResponse(matches);
    }

    private List<MatchPostListElementResponse> toResponse(List<Match> matches) {
        Set<Long> teamIds = matches.stream()
                .flatMap(m -> Stream.of(m.getHostId(), m.getParticipantId()))
                .collect(Collectors.toSet());

        List<Team> teamList = teamAdapter.getAllTeamBy(teamIds);

        Map<Long, Team> teamMap = teamList.stream()
                .collect(Collectors.toMap(Team::getId, Function.identity()));

        return matches.stream()
                .map(m -> {
                    Team host = teamMap.get(m.getHostId());
                    Team participant = teamMap.getOrDefault(m.getParticipantId(), null);
                    return MatchPostListElementResponse.of(
                            m.getId(),
                            host.getName(),
                            participant != null ? participant.getName() : null,
                            m.getStartTime(),
                            m.getEndTime()
                    );
                })
                .toList();
    }
}
