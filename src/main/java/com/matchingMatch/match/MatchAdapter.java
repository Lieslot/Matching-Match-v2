package com.matchingMatch.match;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.team.domain.entity.TeamEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MatchAdapter {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;


    public Match getMatchBy(Long matchId) {
        MatchEntity matchEntity = matchRepository.findById(matchId).orElse(null);
        TeamEntity hostEntity = teamRepository.findById(matchEntity.getHostId()).orElseThrow(() -> new IllegalArgumentException());
        TeamEntity participantEntity = teamRepository.findById(matchEntity.getParticipantId()).orElse(null);

        Team host = hostEntity.toDomain();
        Team participant = participantEntity.toDomain();
        return Match.builder()
                .id(matchEntity.getId())
                .host(host)
                .participant(participant)
                .startTime(matchEntity.getStartTime())
                .endTime(matchEntity.getEndTime())
                .build();
    }
    // TODO 추후에 페이징 추가
    public List<Match> getMatches() {
        List<MatchEntity> matchEntities = matchRepository.findAll();
        return matchEntities.stream()
                .map(matchEntity -> {
                    return getMatchBy(matchEntity.getId());

                })
                .toList();
    }



}
