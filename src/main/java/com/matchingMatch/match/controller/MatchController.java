package com.matchingMatch.match.controller;


import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.dto.MatchPostRequest;
import com.matchingMatch.match.dto.MatchPostResponse;
import com.matchingMatch.match.service.MatchService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
public class MatchController {

    private final MatchService matchService;

    @PostMapping(value = "/create")
    public ResponseEntity<String> createNewMatchPost(@Valid @RequestBody MatchPostRequest matchPostRequest) {

        Match newMatch = matchPostRequest.toEntity();

        Long id = matchService.save(newMatch);

        URI redirectUri = URI.create(String.format("/match/post/%d", id));
        return ResponseEntity.created(redirectUri).build();

    }

    @GetMapping(value = "/{id}")
    public MatchPostResponse getMatchPost(@PathVariable Long id) {

        Match matchPost = matchService.getMatchPostBy(id);

        return MatchPostResponse.builder()
                .hostId(matchPost.getHostId())
                .participantId(matchPost.getParticipantId())
                .stadiumCost(matchPost.getStadiumCost())
                .startTime(matchPost.getStartTime())
                .endTime(matchPost.getEndTime())
                .gender(matchPost.getGender())
                .etc(matchPost.getEtc())
                .build();

    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteMatchPost(@PathVariable Long id) {

        Match matchPost = matchService.getMatchPostBy(id);

        matchService.deleteMatchPostBy(id);

        return ResponseEntity.noContent().build();

    }


    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Void> updateMatchPost(@PathVariable Long id,
                                             @Valid @RequestBody MatchPostRequest matchPostRequest) {

        Match updatedMatchPost = matchPostRequest.toEntity();
        matchService.updateMatchPostBy(updatedMatchPost);

        URI redirectUri = URI.create(String.format("/match/post/%d", id));

        return ResponseEntity.created(redirectUri).build();

    }





}
