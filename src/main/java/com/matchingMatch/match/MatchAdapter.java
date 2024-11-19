package com.matchingMatch.match;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.match.domain.entity.StadiumEntity;
import com.matchingMatch.match.domain.repository.MannerRateCheckRepository;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.StadiumRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.team.domain.entity.TeamEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MatchAdapter {

    private final MatchRepository matchRepository;
    private final MannerRateCheckRepository mannerRateCheckRepository;
    private final TeamRepository teamRepository;
    private final StadiumRepository stadiumRepository;

    @Transactional(readOnly = true)
    public Match getMatchBy(Long matchId) {
        MatchEntity matchEntity = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("매치를 찾을 수 없습니다. id: " + matchId));
        TeamEntity hostEntity = teamRepository.findById(matchEntity.getHostId())
                .orElseThrow(() -> new IllegalArgumentException("주최팀을 찾을 수 없습니다. id: " + matchEntity.getHostId()));
        TeamEntity participantEntity = teamRepository.findById(matchEntity.getParticipantId()).orElse(null);

        StadiumEntity stadiumEntity = stadiumRepository.findById(matchEntity.getStadiumId())
                .orElseThrow(() -> new IllegalArgumentException("경기장을 찾을 수 없습니다. id: " + matchEntity.getStadiumId()));

        Team host = hostEntity.toDomain();
        Team participant = participantEntity != null ? participantEntity.toDomain() : null;

        return Match.builder()
                .id(matchEntity.getId())
                .host(host)
                .participant(participant)
                .startTime(matchEntity.getStartTime())
                .endTime(matchEntity.getEndTime())
                .stadium(stadiumEntity.toDomain())
                .build();
    }

    @Transactional(readOnly = true)
    public Match getMatchWithRateCheck(Long matchId) {
        Match match = getMatchBy(matchId);
        mannerRateCheckRepository.findByMatchId(matchId)
                .ifPresent(mannerRateCheckEntity -> {
                    match.setHostRate(mannerRateCheckEntity.getIsHostRate());
                    match.setParticipantRate(mannerRateCheckEntity.getIsParticipantRate());
                });
        return match;
    }

    // TODO 추후에 페이징 추가
    @Transactional(readOnly = true)
    public List<Match> getMatches() {
        List<MatchEntity> matchEntities = matchRepository.findAll();
        return matchEntities.stream()
                .map(matchEntity -> {
                    return getMatchBy(matchEntity.getId());

                })
                .toList();
    }

    public void updateMatch(Match match) {
        MatchEntity from = MatchEntity.from(match);
        matchRepository.save(from);
    }

}
