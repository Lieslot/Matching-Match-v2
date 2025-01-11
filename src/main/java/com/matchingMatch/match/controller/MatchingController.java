package com.matchingMatch.match.controller;


import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.domain.MannerRate;
import com.matchingMatch.match.dto.MannerRateRequest;
import com.matchingMatch.match.dto.MatchCancelConfirmedRequest;
import com.matchingMatch.match.dto.MatchCancelRequest;
import com.matchingMatch.match.dto.MatchConfirmRequest;
import com.matchingMatch.match.dto.MatchRefuseRequest;
import com.matchingMatch.match.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirmMatchRequest(
            @Valid @RequestBody MatchConfirmRequest matchConfirmRequest,
            @Authentication UserAuth userAuth) {

        matchService.confirmMatchRequest(matchConfirmRequest.getPostId(), userAuth.getId(), matchConfirmRequest.getRequestingTeamId());
    }

    @AuthenticatedUser
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelMatchRequest(
            @Valid @RequestBody MatchCancelRequest matchCancelRequest,
            @Authentication UserAuth userAuth) {

        matchService.cancelMatchRequest(matchCancelRequest.getMatchRequestId(), userAuth.getId());

    }

    @AuthenticatedUser
    @DeleteMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelConfirmedMatchRequest(
            @Authentication UserAuth userAuth,
            @Valid @RequestBody MatchCancelConfirmedRequest matchCancelConfirmedRequest) {
        matchService.cancelConfirmedMatch(matchCancelConfirmedRequest.getMatchId(), userAuth.getId());
    }

    @AuthenticatedUser
    @DeleteMapping("/refuse")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refuseMatchRequest(
            @Valid @RequestBody MatchRefuseRequest matchRefuseRequest,
            @Authentication UserAuth userAuth) {
        matchService.refuseMatchRequest(matchRefuseRequest.getMatchRequestId(), userAuth.getId());
    }

    @PostMapping("/rate")
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.OK)
    public void rateMannerPoint(
            @Valid @RequestBody MannerRateRequest mannerRateRequest,
            @Authentication UserAuth userAuth) {
        // 구현
        MannerRate mannerRate = new MannerRate(userAuth.getId(), mannerRateRequest.getRate());
        matchService.rateMannerPoint(mannerRateRequest.getMatchId(), mannerRate);
    }

}
