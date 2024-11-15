package com.matchingMatch.match.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class MatchPostsResponse {
    public MatchPostsResponse(List<MatchPostListElementResponse> matchPosts) {
        this.matchPosts = matchPosts;
    }

    private List<MatchPostListElementResponse> matchPosts;

}
