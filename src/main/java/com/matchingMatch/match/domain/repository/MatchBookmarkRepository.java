package com.matchingMatch.match.domain.repository;

import com.matchingMatch.team.domain.entity.MatchBookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MatchBookmarkRepository extends JpaRepository<MatchBookmarkEntity, Long> {
}
