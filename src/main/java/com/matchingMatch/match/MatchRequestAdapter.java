package com.matchingMatch.match;

import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import com.matchingMatch.match.domain.repository.MatchRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchRequestAdapter {

    private final MatchRequestRepository matchRequestRepository;

    public void deleteByMatchIdAndSendTeamId(Long matchId, Long teamId) {
        matchRequestRepository.deleteByMatchIdAndSendTeamId(matchId, teamId);
    }

    public void deleteAllByMatchId(Long matchId) {
        matchRequestRepository.deleteAllByMatchId(matchId);
    }

    public void save(MatchRequestEntity matchRequestEntity) {
        matchRequestRepository.save(matchRequestEntity);
    }

    public void deleteById(Long matchRequestId) {
        matchRequestRepository.deleteById(matchRequestId);
    }

    public MatchRequestEntity findById(Long matchRequestId) {
        return matchRequestRepository.findById(matchRequestId)
                .orElseThrow(IllegalArgumentException::new);  }
}
