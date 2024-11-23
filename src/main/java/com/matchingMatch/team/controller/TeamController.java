package com.matchingMatch.team.controller;


import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.dto.TeamProfileResponse;
import com.matchingMatch.match.dto.TeamProfileUpdateRequest;
import com.matchingMatch.match.service.MatchBookmarkService;
import com.matchingMatch.team.dto.TeamRegisterRequest;
import com.matchingMatch.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {


    private final TeamService teamService;
    private final MatchBookmarkService matchBookmarkService;


    @AuthenticatedUser
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerTeam(
            TeamRegisterRequest teamRegisterRequest,
            @Authentication UserAuth userAuth) {

        teamService.registerTeam(teamRegisterRequest, userAuth.getId());
    }

    @AuthenticatedUser
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeam(
            @Authentication UserAuth userAuth) {

        teamService.deleteTeam(userAuth.getId());
    }

    @AuthenticatedUser
    @PutMapping("leader")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeLeader(
            @Authentication UserAuth userAuth) {

    }


    @AuthenticatedUser
    @GetMapping("/profile")
    public TeamProfileResponse getTeamProfile(
            @Authentication UserAuth userAuth) {

        return teamService.getTeamProfile(userAuth.getId());

    }

    @AuthenticatedUser
    @PutMapping("/profile")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTeamProfile(
            @Authentication UserAuth userAuth,
            @RequestBody TeamProfileUpdateRequest teamProfileUpdateRequest) {

        teamService.updateTeamProfile(teamProfileUpdateRequest, userAuth.getId());

    }


}
