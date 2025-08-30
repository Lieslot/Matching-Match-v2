package com.matchingMatch.match.implement;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.matchingMatch.match.domain.repository.TeamRepository;
import com.matchingMatch.team.TeamNotFoundException;
import com.matchingMatch.team.domain.entity.LeaderRequestEntity;
import com.matchingMatch.team.domain.Team;
import com.matchingMatch.team.domain.entity.TeamEntity;
import com.matchingMatch.team.domain.repository.LeaderRequestRepository;
import com.matchingMatch.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TeamAdapter {

	private final TeamRepository teamRepository;
	private final UserRepository userRepository;
	private final LeaderRequestRepository leaderRequestRepository;

	@Transactional(readOnly = true)
	public Team getTeamBy(Long teamId) {

		TeamEntity team = teamRepository.findById(teamId)
			.orElseThrow(() -> new TeamNotFoundException(teamId));

		return team.toDomain();
	}

	public TeamEntity getTeamEntityBy(Long teamId) {
		return teamRepository.findById(teamId)
			.orElseThrow(() -> new TeamNotFoundException(teamId));
	}

	public TeamEntity getTeamEntityByLeaderId(Long userId) {
		return teamRepository.findByLeaderId(userId)
			.orElseThrow(() -> new TeamNotFoundException(userId));
	}

	public Boolean existsById(Long teamId) {
		return teamRepository.existsById(teamId);
	}

	public Long save(Team team) {
		return teamRepository.save(TeamEntity.of(team)).getId();
	}


	public void save(LeaderRequestEntity leaderRequestEntity) {
		leaderRequestRepository.save(leaderRequestEntity);
	}

	public void delete(Long teamId) {
		teamRepository.deleteById(teamId);
	}

	public LeaderRequestEntity getLeaderRequestByTeamID(Long teamId) {
		return leaderRequestRepository.findByTeamId(teamId)
			.orElseThrow(IllegalArgumentException::new);
	}

	public void deleteLeaderRequestByTeamId(Long teamId) {
		leaderRequestRepository.deleteByTeamId(teamId);
	}

	public Long countUserTeam(Long userId) {
		return teamRepository.countAllByLeaderId(userId);
	}
	@Transactional(readOnly = true)
	public Team getTeamByLeaderId(Long userId) {
		TeamEntity teamEntity = teamRepository.findByLeaderId(userId)
			.orElseThrow(() -> new IllegalArgumentException("Team Not Found" + "userId: " + userId));
		return teamEntity.toDomain();
	}

	public List<Team> getAllTeamBy(Collection<Long> teamIds) {
		return teamRepository.findAllById(teamIds).stream()
			.map(TeamEntity::toDomain)
			.toList();
	}
}
