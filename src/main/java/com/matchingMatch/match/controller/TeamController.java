package com.matchingMatch.match.controller;


import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.dto.MatchBookMarkRemoveRequest;
import com.matchingMatch.match.dto.MatchBookmarkRequest;
import com.matchingMatch.match.dto.TeamProfileResponse;
import com.matchingMatch.match.dto.TeamProfileUpdateRequest;
import com.matchingMatch.match.service.MatchBookmarkService;
import com.matchingMatch.match.service.TeamService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {


    private final TeamService teamService;
    private final MatchBookmarkService matchBookmarkService;

    @AuthenticatedUser
    @PostMapping("/bookmark")
    public ResponseEntity<Void> storeMatchBookmark(
            @RequestBody MatchBookmarkRequest matchBookmarkRequest,
            @Authentication UserAuth userAuth) {

        matchBookmarkService.addMatchBookmark(userAuth.getId(), matchBookmarkRequest.getMatchId());

        return ResponseEntity.ok().build();
    }

    @AuthenticatedUser
    @DeleteMapping("/bookmark")
    public ResponseEntity<Void> removeMatchBookmark(
            @RequestBody MatchBookMarkRemoveRequest matchBookmarkRemoveRequest,
            @Authentication UserAuth userAuth) {

        matchBookmarkService.removeMatchBookmark(matchBookmarkRemoveRequest.getMatchBookmarkId(),
                userAuth.getId());

        return ResponseEntity.ok().build();
    }


    @AuthenticatedUser
    @GetMapping("/profile")
    public TeamProfileResponse getTeamProfile(
            @Authentication UserAuth userAuth) {

        return teamService.getTeamProfile(userAuth.getId());

    }

    @AuthenticatedUser
    @PostMapping("/profile")
    public ResponseEntity<Void> updateTeamProfile(
            @Authentication UserAuth userAuth,
            @RequestBody TeamProfileUpdateRequest teamProfileUpdateRequest) {

        teamService.updateTeamProfile(userAuth.getId(), teamProfileUpdateRequest);

        return ResponseEntity.created(URI.create("/profile")).build();
    }


}
