package com.matchingMatch.match.service;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.repository.MatchRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;



    public void save(Match match) {
        Match save = matchRepository.save(match);
    }

    public Match getMatchPostBy(Long matchId) {

        Optional<Match> result = matchRepository.findById(matchId);
        return result.orElse(null);
    }



}
