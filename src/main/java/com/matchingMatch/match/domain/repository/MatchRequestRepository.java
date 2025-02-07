package com.matchingMatch.match.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchingMatch.match.domain.entity.MatchRequestEntity;

public interface MatchRequestRepository extends JpaRepository<MatchRequestEntity, Long> {

	List<MatchRequestEntity> findAllBySendTeamId(Long teamId);

	List<MatchRequestEntity> findAllByTargetTeamId(Long teamId);


	List<MatchRequestEntity> findAllByMatchId(Long matchId);

	Optional<MatchRequestEntity> findBySendTeamIdAndMatchId(Long teamId, Long matchId);

	void deleteAllByMatchId(Long matchId);

	void deleteByMatchIdAndSendTeamId(Long matchId, Long teamId);

	void deleteAllBySendTeamId(Long teamId);

	void deleteAllByTargetTeamId(Long teamId);
}


