package com.matchingMatch.match.controller;


import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.dto.MannerRateRequest;
import com.matchingMatch.match.dto.MatchCancelRequest;
import com.matchingMatch.match.dto.MatchConfirmRequest;
import com.matchingMatch.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match/matching")
public class MatchingController {


    private final MatchService matchService;


    @AuthenticatedUser
    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void sendMatchRequest(
            @PathVariable Long postId,
            @Authentication UserAuth userAuth) {

        matchService.sendMatchRequest(postId, userAuth.getId());
    }


    @AuthenticatedUser
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void confirmMatchRequest(
            @RequestBody MatchConfirmRequest matchConfirmRequest,
            @Authentication UserAuth userAuth) {

        matchService.confirmMatchRequest(matchConfirmRequest.getPostId(), userAuth.getId(), matchConfirmRequest.getRequestingTeamId());
    }

    @AuthenticatedUser
    @DeleteMapping("/{matchRequestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelMatchRequest(
            @PathVariable Long matchRequestId,
            @Authentication UserAuth userAuth) {

        matchService.cancelMatchRequest(matchRequestId, userAuth.getId());

    }

    @AuthenticatedUser
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelConfirmedMatchRequest(
            @Authentication UserAuth userAuth,
            @RequestBody MatchCancelRequest matchCancelRequest) {
        matchService.cancelConfirmedMatch(matchCancelRequest.getMatchId(), userAuth.getId());
    }

    @PostMapping("/rate")
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.OK)
    public void rateMannerPoint(
            @RequestBody MannerRateRequest mannerRateRequest,
            @Authentication UserAuth userAuth) {
        // 구현
    }

}
