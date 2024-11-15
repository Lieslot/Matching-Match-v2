package com.matchingMatch.admin.dto;

import com.matchingMatch.admin.NoticePost;
import java.util.List;

public class GetNoticeResponse {

    List<NoticePost> noticePosts;

    public GetNoticeResponse(List<NoticePost> noticePosts) {
        this.noticePosts = noticePosts;
    }
}
