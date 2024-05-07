package com.matchingMatch.match.controller;


import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.dto.MatchCancelRequest;
import com.matchingMatch.match.dto.MatchConfirmRequest;
import com.matchingMatch.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match/matching")
public class MatchingController {


    private final MatchService matchService;



    @AuthenticatedUser
    @PostMapping("/{matchPostId}")
    public ResponseEntity<Void> sendMatchRequest(
            @PathVariable Long matchPostId,
            @Authentication UserAuth userAuth) {

        try {
            matchService.addMatchRequest(matchPostId, userAuth.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        return ResponseEntity.ok().build();

    }


    @AuthenticatedUser
    @PostMapping
    public ResponseEntity<Void> confirmMatchRequest(
            @RequestBody MatchConfirmRequest matchConfirmRequest,
            @Authentication UserAuth userAuth) {

        Long matchPostId = matchConfirmRequest.getMatchId();
        Long confirmedTeamId = matchConfirmRequest.getRequestingTeamId();
        Long hostId = userAuth.getId();

        try {
            matchService.confirmMatchRequest(matchPostId, hostId, confirmedTeamId);
        }  catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        return ResponseEntity.ok().build();
    }



    @AuthenticatedUser
    @DeleteMapping("/{matchPostId}")
    public ResponseEntity<Void> cancelMatchRequest(
            @PathVariable Long matchPostId,
            @Authentication UserAuth userAuth
    ) {

        try {
            matchService.cancelMatchRequest(matchPostId, userAuth.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        return ResponseEntity.accepted().build();
    }


    @AuthenticatedUser
    @DeleteMapping
    public ResponseEntity<Void> cancelConfirmedMatchRequest(
            @Authentication UserAuth userAuth,
            @RequestBody MatchCancelRequest matchCancelRequest
    ) {
        Long matchPostId = matchCancelRequest.getMatchId();

        try {
            matchService.cancelConfirmedMatch(matchPostId, userAuth.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.accepted().build();
    }


}
