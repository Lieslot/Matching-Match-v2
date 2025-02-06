package com.matchingMatch.match.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchingMatch.match.domain.entity.MannerRateCheckEntity;

public interface MannerRateCheckRepository extends JpaRepository<MannerRateCheckEntity, Long> {

	Optional<MannerRateCheckEntity> findByMatchId(Long matchId);

	void deleteAllByMatchIdIn(List<Long> matchId);

	void deleteByMatchId(Long matchId);

	List<MannerRateCheckEntity> findAllByMatchIdIn(Collection<Long> matchIds);
}
