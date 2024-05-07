package com.matchingMatch.match.controller;


import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.dto.MatchPostRequest;
import com.matchingMatch.match.dto.MatchPostResponse;
import com.matchingMatch.match.service.MatchService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/match/post")
@RequiredArgsConstructor
public class MatchPostController {

    private final MatchService matchService;

    @AuthenticatedUser
    @PostMapping(value = "/create")
    public ResponseEntity<String> createNewMatchPost(
            @Valid @RequestBody MatchPostRequest matchPostRequest,
            @Authentication UserAuth userAuth) {

        Match newMatch = matchPostRequest.toEntity();

        try {
            Long postId = matchService.save(newMatch, userAuth.getId());

            URI redirectUri = URI.create(String.format("/match/post/%d", postId));
            return ResponseEntity.created(redirectUri)
                    .build();

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();

        }


    }

    @GetMapping(value = "/{postId}")
    public MatchPostResponse getMatchPost(@PathVariable Long postId) {

        Match matchPost = matchService.getMatchPostBy(postId);

        return MatchPostResponse.builder()
                .hostId(matchPost.getHost())
                .participantId(matchPost.getParticipant())
                .stadiumCost(matchPost.getStadiumCost())
                .startTime(matchPost.getStartTime())
                .endTime(matchPost.getEndTime())
                .gender(matchPost.getGender())
                .etc(matchPost.getEtc())
                .build();

    }

    @AuthenticatedUser
    @DeleteMapping(value = "/delete/{postId}")
    public ResponseEntity<Void> deleteMatchPost(
            @PathVariable Long postId,
            @Authentication UserAuth userAuth) {

        try {
            matchService.deleteMatchPostBy(postId, userAuth.getId());
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();

        }

        return ResponseEntity.noContent()
                .build();

    }

    @AuthenticatedUser
    @PutMapping(value = "/update/{postId}")
    public ResponseEntity<Void> updateMatchPost(
            @PathVariable Long postId,
            @Valid @RequestBody MatchPostRequest matchPostRequest,
            @Authentication UserAuth userAuth) {

        try {
            Match updatedMatchPost = matchPostRequest.toEntity();
            matchService.updateMatch(updatedMatchPost, userAuth.getId());

            URI redirectUri = URI.create(String.format("/match/post/%d", postId));
            return ResponseEntity.created(redirectUri)
                    .build();
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();

        }

    }


}
