package com.matchingMatch.match.domain.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchingMatch.match.domain.entity.MatchEntity;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {

	List<MatchEntity> findAllByParticipantId(Long participantId);

	void deleteAllByHostId(Long hostId);

	List<MatchEntity> findAllByIdIn(Collection<Long> matchIds);

	List<MatchEntity> findAllByHostId(Long hostId);
}
