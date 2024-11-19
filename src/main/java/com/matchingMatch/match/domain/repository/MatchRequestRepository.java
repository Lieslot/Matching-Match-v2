package com.matchingMatch.match.domain.repository;


import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRequestRepository extends JpaRepository<MatchRequestEntity, Long> {

    List<MatchRequestEntity> findBySendTeamId(Long teamId);
    List<MatchRequestEntity> findByTargetTeamId(Long teamId);
    void deleteAllByMatchId(Long matchId);
}
