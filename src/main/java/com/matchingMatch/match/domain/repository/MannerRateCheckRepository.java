package com.matchingMatch.match.domain.repository;

import com.matchingMatch.match.domain.entity.MannerRateCheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MannerRateCheckRepository extends JpaRepository<MannerRateCheckEntity, Long> {

    Optional<MannerRateCheckEntity> findByMatchId(Long matchId);
}