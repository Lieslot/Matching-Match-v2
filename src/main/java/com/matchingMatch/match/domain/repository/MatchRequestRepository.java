package com.matchingMatch.match.domain.repository;


import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRequestRepository extends JpaRepository<MatchRequestEntity, Long> {
}
