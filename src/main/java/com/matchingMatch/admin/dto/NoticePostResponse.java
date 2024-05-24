package com.matchingMatch.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter

public class NoticePostResponse {

    private Long id;

    private String title;

    private String content;
    @Builder
    public NoticePostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
