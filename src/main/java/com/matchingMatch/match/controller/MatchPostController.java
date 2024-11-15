package com.matchingMatch.match.controller;


import com.matchingMatch.admin.dto.ModifyMatchPostRequest;
import com.matchingMatch.admin.dto.ModifyMatchPostResponse;
import com.matchingMatch.admin.dto.PostMatchPostResponse;
import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.dto.PostMatchPostRequest;
import com.matchingMatch.match.dto.GetMatchPostResponse;
import com.matchingMatch.match.dto.MatchPostsResponse;
import com.matchingMatch.match.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchPostController {

    private final MatchService matchService;

    @GetMapping
    public MatchPostsResponse getMatchPosts(
            @RequestParam(value = "LastMatchId", required = false, defaultValue = "0") String matchLastId,
            @RequestParam(value = "LastMatchId", required = false, defaultValue = "20") String pageSize) {

    }


    @AuthenticatedUser
    @PostMapping
    public PostMatchPostResponse createNewMatchPost(
            @Valid @RequestBody PostMatchPostRequest matchPostRequest,
            @Authentication UserAuth userAuth) {

    }


    @GetMapping("/{postId}")
    public GetMatchPostResponse getMatchPost(@PathVariable Long postId) {

    }

    @AuthenticatedUser
    @DeleteMapping("/{postId}")
    public void deleteMatchPost(
            @PathVariable Long postId,
            @Authentication UserAuth userAuth) {


    }

    @AuthenticatedUser
    @PutMapping("/{postId}")
    public ModifyMatchPostResponse updateMatchPost(
            @PathVariable Long postId,
            @Valid @RequestBody ModifyMatchPostRequest matchPostRequest,
            @Authentication UserAuth userAuth) {

    }

    // TODO 내가 요청한 매치 리스트
    @GetMapping("/request")
    @AuthenticatedUser
    public MatchPostsResponse getMyMatchList(@Authentication UserAuth userAuth) {
           
    }

    // TODO 다른 팀이 요청한 매치 리스트
    @GetMapping("/request/other")
    @AuthenticatedUser
    public MatchPostsResponse getOtherMatchList(@Authentication UserAuth userAuth) {

    }

}
