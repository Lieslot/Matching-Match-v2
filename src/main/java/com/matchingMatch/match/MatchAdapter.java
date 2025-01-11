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

import java.util.List;

@Component
@RequiredArgsConstructor
public class MatchAdapter {

    private final MatchRepository matchRepository;
    private final MannerRateCheckRepository mannerRateCheckRepository;
    private final TeamRepository teamRepository;
    private final MatchRequestRepository matchRequestRepository;
    private final StadiumRepository stadiumRepository;


    @Transactional
    public Match getMatchBy2(Long matchId) {
        MatchEntity matchEntity = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(matchId));

        MannerRateCheckEntity mannerRateCheckEntity = mannerRateCheckRepository.findByMatchId(matchId)
                .orElse(MannerRateCheckEntity.from(matchId));

        return Match.builder()
                .id(matchEntity.getId())
                .hostId(matchEntity.getHostId())
                .participantId(matchEntity.getParticipantId())
                .startTime(matchEntity.getStartTime())
                .endTime(matchEntity.getEndTime())
                .stadiumId(matchEntity.getStadiumId())
                .stadiumCost(matchEntity.getStadiumCost())
                .etc(matchEntity.getEtc())
                .isHostRate(mannerRateCheckEntity.getIsHostRate())
                .isParticipantRate(mannerRateCheckEntity.getIsParticipantRate())
                .build();

    }


    // TODO 추후에 페이징 추가
    @Transactional(readOnly = true)
    public List<Match> getMatches() {
        List<MatchEntity> matchEntities = matchRepository.findAll();
        return matchEntities.stream()
                .map(matchEntity -> {
                    return getMatchBy2(matchEntity.getId());

                })
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
//
//    public Long saveMatch(Match match) {
//        MatchEntity from = MatchEntity.from(match);
//        return matchRepository.save(from).getId();
//    }

    public Long save(MatchEntity matchEntity) {
        return matchRepository.save(matchEntity).getId();
    }

    @Transactional(readOnly = true)
    public List<Match> getTeamRequestingMatches(Long teamId) {
        List<MatchRequestEntity> myMatchRequests = matchRequestRepository.findAllBySendTeamId(teamId);

        return myMatchRequests.stream()
                .map(matchRequest -> getMatchBy2(matchRequest.getMatchId()))
                .toList();
    }


    @Transactional(readOnly = true)
    public List<Match> getTeamRequestedMatches(Long teamId) {
        List<MatchRequestEntity> myMatchRequests = matchRequestRepository.findAllByTargetTeamId(teamId);

        return myMatchRequests.stream()
                .map(matchRequest -> getMatchBy2(matchRequest.getMatchId()))
                .toList();
    }

    @Transactional
    public void deleteById(Long matchId) {
        matchRepository.deleteById(matchId);
    }

    @Transactional
    public List<Match> getHostingMatches(Long teamId) {
        return matchRepository.findAllByHostId(teamId).stream()
                .map(match -> {
                    Long id = match.getId();
                    return getMatchBy2(id);
                })
                .filter(match -> {
                    return !match.started();
                })
                .toList();
    }
}
