package com.matchingMatch.match.service;

import com.matchingMatch.admin.NoticePost;
import com.matchingMatch.admin.domain.NoticePostRepository;
import com.matchingMatch.admin.dto.NoticePostRequest;
import com.matchingMatch.admin.service.NoticePostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(NoticePostService.class)
public class NoticePostServiceTest {

    @Autowired
    private NoticePostRepository noticePostRepository;

    @Autowired
    private NoticePostService noticePostService;

    @BeforeEach
    void setUp() {
        noticePostRepository.deleteAll();
    }

    @Test
    void testGetNoticePosts() {
        // Arrange
        NoticePost post1 = NoticePost.builder().title("Title1").content("Content1").build();
        NoticePost post2 = NoticePost.builder().title("Title2").content("Content2").build();
        noticePostRepository.save(post1);
        noticePostRepository.save(post2);

        // Act
        List<NoticePost> result = noticePostService.getNoticePosts(0);

        // Assert
        assertThat(result).hasSize(2)
                .extracting(NoticePost::getTitle)
                .containsExactlyInAnyOrder("Title1", "Title2");
    }

    @Test
    void testGetNoticePost() {
        // Arrange
        NoticePost post = NoticePost.builder().title("Title1").content("Content1").build();
        NoticePost savedPost = noticePostRepository.save(post);

        // Act
        NoticePost result = noticePostService.getNoticePost(savedPost.getId());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Title1");
        assertThat(result.getContent()).isEqualTo("Content1");
    }

    @Test
    void testCreateNoticePost() {
        // Arrange
        NoticePostRequest request = new NoticePostRequest(null, "Title1", "Content1");

        // Act
        Long postId = noticePostService.createNoticePost(request);
        Optional<NoticePost> savedPost = noticePostRepository.findById(postId);

        // Assert
        assertThat(savedPost).isPresent();
        assertThat(savedPost.get().getTitle()).isEqualTo("Title1");
        assertThat(savedPost.get().getContent()).isEqualTo("Content1");
    }

    @Test
    void testUpdateNoticePost() {
        // Arrange
        NoticePost post = NoticePost.builder().title("Title1").content("Content1").build();
        NoticePost savedPost = noticePostRepository.save(post);

        NoticePostRequest updateRequest = new NoticePostRequest(savedPost.getId(), "UpdatedTitle", "UpdatedContent");

        // Act
        noticePostService.updateNoticePost(updateRequest);
        Optional<NoticePost> updatedPost = noticePostRepository.findById(savedPost.getId());

        // Assert
        assertThat(updatedPost).isPresent();
        assertThat(updatedPost.get().getTitle()).isEqualTo("UpdatedTitle");
        assertThat(updatedPost.get().getContent()).isEqualTo("UpdatedContent");
    }

    @Test
    void testDeleteNoticePost() {
        // Arrange
        NoticePost post = NoticePost.builder().title("Title1").content("Content1").build();
        NoticePost savedPost = noticePostRepository.save(post);

        // Act
        noticePostService.deleteNoticePost(savedPost.getId());
        Optional<NoticePost> result = noticePostRepository.findById(savedPost.getId());

        // Assert
        assertThat(result).isNotPresent();
    }



}
