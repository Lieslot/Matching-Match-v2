package com.matchingMatch.team.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.dto.TeamProfileResponse;
import com.matchingMatch.match.dto.TeamProfileUpdateRequest;
import com.matchingMatch.team.dto.LeaderChangeRequest;
import com.matchingMatch.team.dto.LeaderRefuseRequest;
import com.matchingMatch.team.dto.LeaderTransferRequest;
import com.matchingMatch.team.dto.TeamRegisterRequest;
import com.matchingMatch.team.service.TeamService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

	private final TeamService teamService;

	@AuthenticatedUser
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Long registerTeam(
		TeamRegisterRequest teamRegisterRequest,
		@Authentication UserAuth userAuth) {

		return teamService.registerTeam(teamRegisterRequest, userAuth.getId());
	}

	@AuthenticatedUser
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTeam(
		@Authentication UserAuth userAuth) {

		teamService.deleteTeam(userAuth.getId());
	}

	@AuthenticatedUser
	@PostMapping("/leader/accept")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void acceptLeader(
		@Valid @RequestBody LeaderChangeRequest leaderChangeRequest,
		@Authentication UserAuth userAuth) {

		teamService.changeLeader(leaderChangeRequest.getTeamId(), userAuth.getId());
	}

	@AuthenticatedUser
	@PostMapping("/leader/refuse")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void refuseLeader(
		@Valid @RequestBody LeaderRefuseRequest leaderRefuseRequest,
		@Authentication UserAuth userAuth) {

		teamService.refuseLeaderRequest(leaderRefuseRequest.getTeamId(), userAuth.getId());
	}

	@AuthenticatedUser
	@PostMapping("/leader")
	@ResponseStatus(HttpStatus.OK)
	public void requestLeaderTransfer(
		@Valid @RequestBody LeaderTransferRequest leaderTransferRequest,
		@Authentication UserAuth userAuth) {

		teamService.createLeaderRequest(leaderTransferRequest.getTeamId(), leaderTransferRequest.getUsername(),
			userAuth.getId());
	}

	@AuthenticatedUser
	@GetMapping("/profile")
	@ResponseStatus(HttpStatus.OK)
	public TeamProfileResponse getTeamProfile(
		@Authentication UserAuth userAuth) {

		return teamService.getTeamProfile(userAuth.getId());
	}

	@AuthenticatedUser
	@PutMapping("/profile")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void updateTeamProfile(
		@Authentication UserAuth userAuth,
		@Valid @RequestBody TeamProfileUpdateRequest teamProfileUpdateRequest) {

		teamService.updateTeamProfile(teamProfileUpdateRequest, userAuth.getId());
	}

}
