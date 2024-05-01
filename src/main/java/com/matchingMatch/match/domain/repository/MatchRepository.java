package com.matchingMatch.match.domain.repository;

import com.matchingMatch.match.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {


}
