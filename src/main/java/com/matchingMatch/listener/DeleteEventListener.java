package com.matchingMatch.listener;

import com.matchingMatch.listener.event.MatchDeleteEvent;
import com.matchingMatch.match.domain.entity.MatchEntity;
import com.matchingMatch.match.domain.repository.MannerRateCheckRepository;
import com.matchingMatch.match.domain.repository.MatchRepository;
import com.matchingMatch.match.domain.repository.MatchRequestRepository;
import com.matchingMatch.team.domain.repository.LeaderRequestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteEventListener {

    private final MatchRequestRepository matchRequestRepository;
    private final LeaderRequestRepository leaderRequestRepository;
    private final MatchRepository matchRepository;
    private final MannerRateCheckRepository mannerRateCheckRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void deleteTeamEvent(TeamDeleteEvent teamDeleteEvent) {
        Long teamId = teamDeleteEvent.getTeamId();
        matchRequestRepository.deleteAllBySendTeamId(teamId);
        matchRequestRepository.deleteAllByTargetTeamId(teamId);
        leaderRequestRepository.deleteAllByTeamId(teamId);
        List<MatchEntity> ownMatches = matchRepository.findAllByHostId(teamId);
        List<Long> ownMatchIds = ownMatches.stream().map(MatchEntity::getId).toList();
        mannerRateCheckRepository.deleteAllByMatchIdIn(ownMatchIds);
        matchRepository.deleteAllByHostId(teamId);

        List<MatchEntity> participants = matchRepository.findAllByParticipantId(teamId)
                .stream()
                .peek(matchEntity -> matchEntity.setParticipantId(null)).toList();

        matchRepository.saveAll(participants);
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void deleteMatchEvent(MatchDeleteEvent matchDeleteEvent) {

        Long matchId = matchDeleteEvent.getMatchId();

        matchRequestRepository.deleteAllByMatchId(matchId);
        mannerRateCheckRepository.deleteByMatchId(matchId);
    }

}
