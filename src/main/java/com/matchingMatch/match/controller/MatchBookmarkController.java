package com.matchingMatch.match.controller;


import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.dto.MatchPostsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/match/bookmark")
public class MatchBookmarkController {


    // TODO 유저 북마크 한 매치 목록
    @GetMapping
    @AuthenticatedUser
    public MatchPostsResponse getMatchBookMarkList(@Authentication UserAuth userAuth) {

    }

    // TODO 매치 북마크 추가

    @PostMapping
    @AuthenticatedUser
    public void addBookmark(@Authentication UserAuth userAuth) {

    }

    // TODO 매치 북마크 취소

    @DeleteMapping("/id")
    @AuthenticatedUser
    public void cancelBookmark(@Authentication UserAuth userAuth) {

    }

    // TODO 유저 북마크 조회
    @AuthenticatedUser
    @GetMapping
    public void getBookmark(@Authentication UserAuth userAuth) {

    }


}
