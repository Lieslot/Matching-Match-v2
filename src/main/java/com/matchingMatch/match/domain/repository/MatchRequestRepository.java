package com.matchingMatch.match.domain.repository;


import com.matchingMatch.match.domain.MatchRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRequestRepository extends JpaRepository<MatchRequest, Long> {
}
