package com.matchingMatch.team.domain.repository;

import com.matchingMatch.team.domain.entity.LeaderRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderRequestRepository extends JpaRepository<LeaderRequestEntity, Long> {
}
