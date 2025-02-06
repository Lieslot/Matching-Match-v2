package com.matchingMatch.match;

import java.util.List;

import org.springframework.stereotype.Component;

import com.matchingMatch.match.domain.entity.MatchRequestEntity;
import com.matchingMatch.match.domain.repository.MatchRequestRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MatchRequestAdapter {

	private final MatchRequestRepository matchRequestRepository;

	public void deleteByMatchIdAndSendTeamId(Long matchId, Long teamId) {
		matchRequestRepository.deleteByMatchIdAndSendTeamId(matchId, teamId);
	}

	public void deleteAllByMatchId(Long matchId) {
		matchRequestRepository.deleteAllByMatchId(matchId);
	}

	public void save(MatchRequestEntity matchRequestEntity) {
		matchRequestRepository.save(matchRequestEntity);
	}

	public void deleteById(Long matchRequestId) {
		matchRequestRepository.deleteById(matchRequestId);
	}

	public MatchRequestEntity findById(Long matchRequestId) {
		return matchRequestRepository.findById(matchRequestId)
			.orElseThrow(IllegalArgumentException::new);
	}

	public List<MatchRequestEntity> findAllBySendTeamId(Long teamId) {

		return matchRequestRepository.findAllBySendTeamId(teamId);
	}

	public List<MatchRequestEntity> findAllByTargetTeamId(Long teamId) {

		return matchRequestRepository.findAllByTargetTeamId(teamId);
	}

}
