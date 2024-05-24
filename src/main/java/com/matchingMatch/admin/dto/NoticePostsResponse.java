package com.matchingMatch.admin.dto;

import com.matchingMatch.admin.NoticePost;
import java.util.List;

public class NoticePostsResponse {

    List<NoticePost> noticePosts;

    public NoticePostsResponse(List<NoticePost> noticePosts) {
        this.noticePosts = noticePosts;
    }
}
