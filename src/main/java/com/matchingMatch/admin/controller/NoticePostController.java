package com.matchingMatch.admin.controller;


import com.matchingMatch.admin.NoticePost;
import com.matchingMatch.admin.dto.NoticePostRequest;
import com.matchingMatch.admin.dto.NoticePostResponse;
import com.matchingMatch.admin.dto.NoticePostsResponse;
import com.matchingMatch.admin.service.NoticePostService;
import com.matchingMatch.auth.AuthenticatedAdmin;
import com.matchingMatch.match.service.MatchService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticePostController {

    private final NoticePostService noticePostService;

    @GetMapping
    public NoticePostsResponse getNoticePosts(
            Pageable pageable) {
        List<NoticePost> noticePosts = noticePostService.getNoticePosts(pageable.getPageNumber());
        NoticePostsResponse noticePostsResponse = new NoticePostsResponse(noticePosts);
        return noticePostsResponse;
    }

    @GetMapping("/{postId}")
    public NoticePostResponse getNoticePost(@PathVariable Long postId) {
        NoticePost noticePost = noticePostService.getNoticePost(postId);
        return NoticePostResponse.builder()
                .id(noticePost.getId())
                .title(noticePost.getTitle())
                .content(noticePost.getContent())
                .build();
    }

    @AuthenticatedAdmin
    @PostMapping("/create")
    public ResponseEntity<Void> createNoticePost(NoticePostRequest noticePostRequest) {

        Long postId = noticePostService.createNoticePost(noticePostRequest);
        URI redirectedUri = URI.create(String.format("/notice/%d", postId));
        return ResponseEntity.created(redirectedUri).build();
    }

    @AuthenticatedAdmin
    @PutMapping("/{postId}")
    public ResponseEntity<Void> updateNoticePost(@PathVariable Long postId, NoticePostRequest noticePostRequest) {

        noticePostService.updateNoticePost(noticePostRequest);

        return ResponseEntity.accepted().build();
    }
    @AuthenticatedAdmin
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteNoticePost(@PathVariable Long postId) {

        noticePostService.deleteNoticePost(postId);

        return ResponseEntity.accepted().build();
    }


}
