package com.matchingMatch.match.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchingMatch.team.domain.entity.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
	Long countAllByLeaderId(Long leaderId);

	Optional<TeamEntity> findByLeaderId(Long leaderId);
}
