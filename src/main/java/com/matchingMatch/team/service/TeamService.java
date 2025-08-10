package com.matchingMatch.team.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matchingMatch.listener.TeamDeleteEvent;
import com.matchingMatch.match.implement.TeamAdapter;
import com.matchingMatch.match.dto.TeamProfileResponse;
import com.matchingMatch.match.dto.TeamProfileUpdateRequest;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
import com.matchingMatch.team.domain.entity.LeaderRequestEntity;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.team.dto.TeamRegisterRequest;
import com.matchingMatch.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {

	private final TeamAdapter teamAdapter;
	private final UserRepository userRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Transactional(readOnly = true)
	public TeamProfileResponse getTeamProfile(Long teamId) {
		Team team = teamAdapter.getTeamBy(teamId);

		return TeamProfileResponse.builder()
			.teamName(team.getName())
			.teamLogoUrl(team.getLogoUrl())
			.mannerPoint(team.getMannerPoint())
			.region(team.getRegion())
			.gender(team.getGender())
			.teamDescription(team.getDescription())
			.build();
	}

	public Long registerTeam(TeamRegisterRequest teamRegisterRequest, Long leaderId) {

		Team team = Team.builder()
			.name(teamRegisterRequest.getName())
			.teamDescription(teamRegisterRequest.getDescription())
			.teamLogoUrl(teamRegisterRequest.getLogoUrl())
			.leaderId(leaderId)
			.region(teamRegisterRequest.getRegion())
			.build();

		return teamAdapter.save(team);
	}

	@Transactional
	public void updateTeamProfile(TeamProfileUpdateRequest teamProfileUpdateRequest, Long userId) {

		Long teamId = teamProfileUpdateRequest.getId();
		Team team = teamAdapter.getTeamBy(teamId);

		team.checkLeader(userId);

		team.updateTeamProfile(teamProfileUpdateRequest);

		teamAdapter.save(team);

	}

	@Transactional
	public void deleteTeam(Long id) {
		Team team = teamAdapter.getTeamBy(id);
		team.checkLeader(id);

		teamAdapter.delete(id);

		eventPublisher.publishEvent(new TeamDeleteEvent(id));

	}

	@Transactional
	public void changeLeader(Long teamId, Long userId) {

		LeaderRequestEntity leaderRequest = teamAdapter.getLeaderRequestByTeamID(teamId);

		if (!leaderRequest.hasTargetUserId(userId)) {
			throw new IllegalArgumentException("지정된 새로운 팀장이 아닙니다.");
		}
		teamAdapter.deleteLeaderRequestByTeamId(teamId);

		Team team = teamAdapter.getTeamBy(teamId);
		team.changeLeader(leaderRequest.getTargetUserId());
		teamAdapter.save(team);
	}

	@Transactional
	public void refuseLeaderRequest(Long teamId, Long userId) {

		LeaderRequestEntity leaderRequest = teamAdapter.getLeaderRequestByTeamID(teamId);

		if (!leaderRequest.hasTargetUserId(userId)) {
			throw new UnauthorizedAccessException();
		}

		teamAdapter.deleteLeaderRequestByTeamId(teamId);

		// TODO 알림 날리기
	}

	@Transactional
	public void createLeaderRequest(Long teamId, String targetUsername, Long userId) {

		Team team = teamAdapter.getTeamBy(teamId);
		Long targetUserId = userRepository.findByUsername(targetUsername)
			.orElseThrow(IllegalArgumentException::new).getId();

		if (!team.hasLeader(userId)) {
			throw new UnauthorizedAccessException();
		}

		if (teamAdapter.countUserTeam(targetUserId) >= 1) {
			throw new IllegalArgumentException("유저는 하나의 팀만 가질 수 있습니다." + "userId: " + userId);
		}

		teamAdapter.deleteLeaderRequestByTeamId(teamId); // 기존 요청 삭제

		LeaderRequestEntity leaderRequest = LeaderRequestEntity.builder()
			.teamId(team.getId())
			.sendUserId(userId)
			.targetUserId(targetUserId)
			.build();

		teamAdapter.save(leaderRequest);

		// TODO 알림 날리기
	}
}
