package com.matchingMatch.match.controller;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.dto.ModifyMatchPostRequest;
import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.dto.MatchPostListElementResponse;
import com.matchingMatch.match.dto.PostMatchPostRequest;
import com.matchingMatch.match.dto.MatchPostsResponse;
import com.matchingMatch.match.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchPostController {

    private final MatchService matchService;


    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public MatchPostsResponse getMatchPosts() {

        List<MatchPostListElementResponse> posts = matchService.getPosts();

        return new MatchPostsResponse(posts);
    }


    @AuthenticatedUser
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createNewMatchPost(
            @Valid @RequestBody PostMatchPostRequest matchPostRequest,
            @Authentication UserAuth userAuth) {

        return matchService.postNewMatch(matchPostRequest, userAuth.getId());
    }


    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Match getMatchPost(@PathVariable Long postId) {

        return matchService.getMatch(postId);
    }



    @AuthenticatedUser
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMatchPost(
            @PathVariable Long postId,
            @Authentication UserAuth userAuth) {

        matchService.deleteMatchPost(postId, userAuth.getId());
    }

    @AuthenticatedUser
    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMatchPost(
            @PathVariable Long postId,
            @Valid @RequestBody ModifyMatchPostRequest matchPostRequest,
            @Authentication UserAuth userAuth) {

        matchService.updateMatch(matchPostRequest, userAuth.getId());
    }

    @GetMapping("/requests")
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.OK)
    public MatchPostsResponse getMyMatchList(@Authentication UserAuth userAuth) {
        List<MatchPostListElementResponse> posts = matchService.getMyMatches(userAuth.getId());
        return new MatchPostsResponse(posts);
    }

    @GetMapping("/requests/other")
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.OK)
    public MatchPostsResponse getOtherMatchList(@Authentication UserAuth userAuth) {
        List<MatchPostListElementResponse> posts = matchService.getOtherMatches(userAuth.getId());
        return new MatchPostsResponse(posts);
    }

    @GetMapping("/matches/{teamId}")
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.OK)
    public MatchPostsResponse getHostingMatches(@Authentication UserAuth userAuth, @PathVariable Long teamId) {
        List<MatchPostListElementResponse> posts = matchService.getHostingMatches(teamId);
        return new MatchPostsResponse(posts);
    }

}
