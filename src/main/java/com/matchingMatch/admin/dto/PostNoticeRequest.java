package com.matchingMatch.admin.dto;

import com.matchingMatch.admin.NoticePost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostNoticeRequest {

    private Long id;

    private String title;

    private String content;

    public PostNoticeRequest(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Builder
    public NoticePost toEntity() {
        return NoticePost.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }

}
