package com.matchingMatch.match.service;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    private static final String INVALID_AUTHORITY = "권한이 없는 접근입니다.";

    public Long save(Match match, Long userId) {

        Optional<Team> result = teamRepository.findById(userId);

        if (result.isEmpty()) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

        Team host = result.get();

        match.setHostId(host);
        Match newMatch = matchRepository.save(match);

        return newMatch.getId();
    }

    public Match getMatchPostBy(Long matchId) {

        Optional<Match> result = matchRepository.findById(matchId);
        return result.orElse(null);

    }

    public void deleteMatchPostBy(Long matchId, Long userId) {
        Match matchPost = getMatchPostBy(matchId);

        if (!checkHostUser(matchPost.getHostId(), userId)) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }


        matchRepository.deleteById(matchId);

    }

    public void updateMatch(Match updatedMatchPost, Long userId) {


        if (!checkHostUser(updatedMatchPost.getHostId(), userId)) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

        boolean isExists = matchRepository.existsById(updatedMatchPost.getId());

        if (!isExists) {
            throw new IllegalArgumentException();
        }

        matchRepository.save(updatedMatchPost);

    }

    private boolean checkHostUser(Team hostTeam, Long currentUserId) {
        return currentUserId.equals(hostTeam.getId());
    }



}
