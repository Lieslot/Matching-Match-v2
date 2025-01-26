package com.matchingMatch.match;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.entity.MannerRateCheckEntity;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import com.matchingMatch.match.domain.repository.MannerRateCheckRepository;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.MatchRequestRepository;
import com.matchingMatch.match.domain.repository.StadiumRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.exception.MatchNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MatchAdapter {

    private final MatchRepository matchRepository;
    private final MannerRateCheckRepository mannerRateCheckRepository;
    private final TeamRepository teamRepository;
    private final MatchRequestRepository matchRequestRepository;
    private final StadiumRepository stadiumRepository;


    @Transactional
    public Match getMatchBy(Long matchId) {
        MatchEntity matchEntity = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(matchId));

        MannerRateCheckEntity mannerRateCheckEntity = mannerRateCheckRepository.findByMatchId(matchId)
                .orElse(MannerRateCheckEntity.from(matchId));

        return toMach(matchEntity, mannerRateCheckEntity);

    }

    @Transactional
    public List<Match> getAllMatchesByIds(Collection<Long> matchIds) {
        Map<Long, MatchEntity> matches = matchRepository.findAllByIdIn(matchIds)
                .stream()
                .collect(Collectors.toMap(MatchEntity::getId, Function.identity()));


        List<MannerRateCheckEntity> mannerRateCheckEntities = mannerRateCheckRepository.findAllByMatchIdIn(matchIds);

        return mannerRateCheckEntities.stream()
                .map(mannerRateCheckEntity -> toMach(matches.get(mannerRateCheckEntity.getMatchId()), mannerRateCheckEntity))
                .toList();
    }


    // TODO 추후에 페이징 추가
    @Transactional(readOnly = true)
    public List<Match> getCurrentMatches() {
        Map<Long, MatchEntity> matches = matchRepository.findAll().stream()
                .collect(Collectors.toMap(MatchEntity::getId, Function.identity()));

        Set<Long> matchIds = matches.keySet();

        List<MannerRateCheckEntity> mannerRateCheckEntities = mannerRateCheckRepository.findAllByMatchIdIn(matchIds);

        return mannerRateCheckEntities.stream()
                .map(mannerRateCheckEntity -> toMach(matches.get(mannerRateCheckEntity.getMatchId()), mannerRateCheckEntity))
                .toList();
    }


    public void updateMatch(Match match) {
        MatchEntity matchEntity = MatchEntity.from(match);
        matchRepository.save(matchEntity);
    }

    public void updateMannerRateCheck(Match match) {
        MannerRateCheckEntity rateCheck = mannerRateCheckRepository.findByMatchId(match.getId()).orElse(MannerRateCheckEntity.from(match.getId()));
        rateCheck.setIsHostRate(match.getIsHostRate());
        rateCheck.setIsParticipantRate(match.getIsParticipantRate());
        mannerRateCheckRepository.save(rateCheck);

    }

    public Long save(MatchEntity matchEntity) {
        return matchRepository.save(matchEntity).getId();
    }

    @Transactional(readOnly = true)
    public List<Match> getMatchesByHostId(Long hostId) {
        Map<Long, MatchEntity> matches = matchRepository.findAllByHostId(hostId)
                .stream()
                .collect(Collectors.toMap(MatchEntity::getId, Function.identity()));

        Set<Long> matchIds = matches.keySet();

        List<MannerRateCheckEntity> mannerRateCheckEntities = mannerRateCheckRepository.findAllByMatchIdIn(matchIds);

        return mannerRateCheckEntities.stream()
                .map(mannerRateCheckEntity -> toMach(matches.get(mannerRateCheckEntity.getMatchId()), mannerRateCheckEntity))
                .toList();

    }


    @Transactional
    public List<Match> getMatchesByParticipantId(Long participantId) {
        Map<Long, MatchEntity> matches = matchRepository.findAllByParticipantId(participantId)
                .stream()
                .collect(Collectors.toMap(MatchEntity::getId, Function.identity()));

        Set<Long> matchIds = matches.keySet();

        List<MannerRateCheckEntity> mannerRateCheckEntities = mannerRateCheckRepository.findAllByMatchIdIn(matchIds);

        return mannerRateCheckEntities.stream()
                .map(mannerRateCheckEntity -> toMach(matches.get(mannerRateCheckEntity.getMatchId()), mannerRateCheckEntity))
                .toList();
    }


    @Transactional
    public void deleteById(Long matchId) {
        matchRepository.deleteById(matchId);
    }



    private Match toMach(MatchEntity matchEntity, MannerRateCheckEntity mannerRateCheckEntity) {
        return Match.builder()
                .id(matchEntity.getId())
                .hostId(matchEntity.getHostId())
                .participantId(matchEntity.getParticipantId())
                .startTime(matchEntity.getStartTime())
                .endTime(matchEntity.getEndTime())
                .stadiumId(matchEntity.getStadiumId())
                .stadiumCost(matchEntity.getStadiumCost())
                .confirmedTime(matchEntity.getConfirmedTime())
                .etc(matchEntity.getEtc())
                .gender(matchEntity.getGender())
                .isHostRate(mannerRateCheckEntity.getIsHostRate())
                .isParticipantRate(mannerRateCheckEntity.getIsParticipantRate())
                .build();
    }
}
