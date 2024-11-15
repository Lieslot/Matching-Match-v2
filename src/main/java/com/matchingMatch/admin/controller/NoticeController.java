package com.matchingMatch.admin.controller;


import com.matchingMatch.admin.dto.ModifyNoticeRequest;
import com.matchingMatch.admin.dto.ModifyNoticeResponse;
import com.matchingMatch.admin.dto.PostNoticeRequest;
import com.matchingMatch.admin.dto.GetNoticeResponse;
import com.matchingMatch.admin.dto.PostNoticeResponse;
import com.matchingMatch.admin.service.NoticePostService;
import com.matchingMatch.auth.AuthenticatedAdmin;
import com.matchingMatch.auth.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticePostService noticePostService;

    @GetMapping
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.OK)
    public List<GetNoticeResponse> getNoticePosts(Pageable pageable) {
    }

    @GetMapping("/{postId}")
    @AuthenticatedUser
    @ResponseStatus(HttpStatus.OK)
    public GetNoticeResponse getNoticePost(@PathVariable Long postId) {
    }

    @AuthenticatedAdmin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostNoticeResponse createNoticePost(PostNoticeRequest noticePostRequest) {
    }

    @AuthenticatedAdmin
    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ModifyNoticeResponse updateNoticePost(@PathVariable Long postId, ModifyNoticeRequest noticePostRequest) {
    }


    @AuthenticatedAdmin
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNoticePost(@PathVariable Long postId) {


    }


}
