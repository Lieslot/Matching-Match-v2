package com.matchingMatch.admin.service;

import com.matchingMatch.admin.NoticePost;
import com.matchingMatch.admin.domain.NoticePostRepository;
import com.matchingMatch.admin.dto.PostNoticeRequest;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NoticePostService {

    private final NoticePostRepository noticePostRepository;

    // TODO R
    public List<NoticePost> getNoticePosts(int pageNumber) {

        return noticePostRepository.findAll(PageRequest.of(pageNumber, 50)).getContent();

    }
    public NoticePost getNoticePost(Long postId) {

        Optional<NoticePost> searchResult  = noticePostRepository.findById(postId);

        if (searchResult.isEmpty()) {
            return null;
        }

        return searchResult.get();
    }

    // TODO C
    public Long createNoticePost(PostNoticeRequest noticePostCreateRequest) {
        NoticePost noticePost = noticePostCreateRequest.toEntity();
        NoticePost savedPost = noticePostRepository.save(noticePost);

        return savedPost.getId();
    }

    // TODO U
    public void updateNoticePost(PostNoticeRequest noticePostUpdateRequest) {
        Optional<NoticePost> searchResult  = noticePostRepository.findById(noticePostUpdateRequest.getId());

        if (searchResult.isEmpty()) {
            return;
        }

        NoticePost noticePost = searchResult.get();
        noticePost.updatePost(noticePostUpdateRequest.getTitle(), noticePostUpdateRequest.getContent());

    }
    // TODO D
    public void deleteNoticePost(Long postId) {

        noticePostRepository.deleteById(postId);
    }

}
