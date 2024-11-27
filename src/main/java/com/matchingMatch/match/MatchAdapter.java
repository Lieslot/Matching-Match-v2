package com.matchingMatch.match;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import com.matchingMatch.match.domain.entity.StadiumEntity;
import com.matchingMatch.match.domain.repository.MannerRateCheckRepository;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.MatchRequestRepository;
import com.matchingMatch.match.domain.repository.StadiumRepository;
import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.match.exception.HostNotFoundException;
import com.matchingMatch.match.exception.MatchNotFoundException;
import com.matchingMatch.match.exception.StadiumNotFound;
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
    private final MatchRequestRepository matchRequestRepository;
    private final StadiumRepository stadiumRepository;

    @Transactional(readOnly = true)
    public Match getMatchBy(Long matchId) {
        MatchEntity matchEntity = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(matchId));
        TeamEntity hostEntity = teamRepository.findById(matchEntity.getHostId())
                .orElseThrow(() -> new HostNotFoundException(matchEntity.getHostId()));
        TeamEntity participantEntity = teamRepository.findById(matchEntity.getParticipantId()).orElse(null);

        StadiumEntity stadiumEntity = stadiumRepository.findById(matchEntity.getStadiumId())
                .orElseThrow(() -> new StadiumNotFound(matchEntity.getStadiumId()));

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
                .map(matchRequest -> getMatchBy(matchRequest.getMatchId()))
                .toList();
    }


    @Transactional(readOnly = true)
    public List<Match> getTeamRequestedMatches(Long teamId) {
        List<MatchRequestEntity> myMatchRequests = matchRequestRepository.findAllByTargetTeamId(teamId);

        return myMatchRequests.stream()
                .map(matchRequest -> getMatchBy(matchRequest.getMatchId()))
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
                    return getMatchBy(id);
                })
                .filter(match -> {
                    return !match.started();
                })
                .toList();
    }
}
