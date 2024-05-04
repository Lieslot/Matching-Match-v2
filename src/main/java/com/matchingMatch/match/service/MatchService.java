package com.matchingMatch.match.service;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.repository.MatchRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public Long save(Match match) {
        Team hostId = match.getHostId();

        Match newMatch = matchRepository.save(match);

        return newMatch.getId();
    }

    public Match getMatchPostBy(Long matchId) {

        Optional<Match> result = matchRepository.findById(matchId);
        return result.orElse(null);
    }

    public void deleteMatchPostBy(Long matchId) {

        matchRepository.deleteById(matchId);

    }

    public void updateMatchPostBy(Match updatedMatchPost) {

        boolean isExists = matchRepository.existsById(updatedMatchPost.getId());

        if (!isExists) {
            throw new IllegalArgumentException();
        }

        matchRepository.save(updatedMatchPost);

    }




}
