package com.matchingMatch.team.domain.repository;

import com.matchingMatch.team.domain.entity.LeaderRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaderRequestRepository extends JpaRepository<LeaderRequestEntity, Long> {
    Optional<LeaderRequestEntity> findByTeamId(Long teamId);
    void deleteByTeamId(Long teamId);
}
