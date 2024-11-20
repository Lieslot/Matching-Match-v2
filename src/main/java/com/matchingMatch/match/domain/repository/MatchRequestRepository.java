package com.matchingMatch.match.domain.repository;


import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRequestRepository extends JpaRepository<MatchRequestEntity, Long> {

    List<MatchRequestEntity> findAllBySendTeamId(Long teamId);
    List<MatchRequestEntity> findAllByTargetTeamId(Long teamId);
    void deleteAllByMatchId(Long matchId);
    void deleteByMatchIdAndSendTeamId(Long matchId, Long teamId);
}
