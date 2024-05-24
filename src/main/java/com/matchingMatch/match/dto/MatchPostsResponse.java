package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.Match;
import java.util.List;
import lombok.Getter;

@Getter
public class MatchPostsResponse {
    public MatchPostsResponse(List<Match> matchPosts) {
        this.matchPosts = matchPosts;
    }

    private List<Match> matchPosts;

}
