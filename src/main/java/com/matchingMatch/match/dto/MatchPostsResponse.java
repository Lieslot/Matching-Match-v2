package com.matchingMatch.match.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MatchPostsResponse {

	@NotNull
	public MatchPostsResponse(List<MatchPostListElementResponse> matchPosts) {
		this.matchPosts = matchPosts;
	}

	@NotNull
	private List<MatchPostListElementResponse> matchPosts;

}
