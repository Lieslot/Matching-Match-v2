package com.matchingMatch.team.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchingMatch.team.domain.entity.LeaderRequestEntity;

public interface LeaderRequestRepository extends JpaRepository<LeaderRequestEntity, Long> {
	Optional<LeaderRequestEntity> findByTeamId(Long teamId);

	void deleteByTeamId(Long teamId);

	void deleteAllByTeamId(Long teamId);
}
