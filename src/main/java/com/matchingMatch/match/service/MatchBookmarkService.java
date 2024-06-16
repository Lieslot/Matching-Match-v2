package com.matchingMatch.match.service;

import com.matchingMatch.team.domain.MatchBookmark;
import com.matchingMatch.team.domain.Team;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MatchBookmarkService {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    @Transactional
    public void addMatchBookmark(Long teamId, Long matchId) {

        Optional<Team> teamSearchResult = teamRepository.findById(teamId);
        boolean checkMatchExists = matchRepository.existsById(matchId);

        if (teamSearchResult.isEmpty() || !checkMatchExists) {

            throw new IllegalArgumentException();

        }
        Team team = teamSearchResult.get();


        MatchBookmark matchBookMark = MatchBookmark.builder()
                .team(team)
                .storedMatchId(matchId)
                .build();

        team.addMatchBookMark(matchBookMark);
        teamRepository.save(team); // matchBookmark의 영속화를 위해 필요함.

    }


    @Transactional
    public void removeMatchBookmark(Long matchBookmarkId, Long teamId) {

        Optional<Team> teamSearchResult = teamRepository.findById(teamId);

        if (teamSearchResult.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Team team = teamSearchResult.get();

        team.removeMatchBookmark(matchBookmarkId);
    }

}
