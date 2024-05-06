package com.matchingMatch.match.service;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.MatchRequest;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private static final String INVALID_AUTHORITY = "권한이 없는 접근입니다.";
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final WebInvocationPrivilegeEvaluator privilegeEvaluator;

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

    // TODO 매치 신청 로직 추가
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

    // TODO 매치 신청 취소 로직 추가
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
    
    
    // TODO 매치 신청 확정 로직
    public void confirmMatchRequest(Long matchId, Long currentUserId, Long confirmedTeamId) {

        Optional<Match> matchResult = matchRepository.findById(matchId);
        Optional<Team> teamResult = teamRepository.findById(confirmedTeamId);

        if (teamResult.isEmpty() || matchResult.isEmpty()) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

        Team participantTeam = teamResult.get();
        Match match = matchResult.get();

        checkHostUser(match.getHost(), currentUserId);

        match.confirmParticipant(participantTeam);
    }


}
