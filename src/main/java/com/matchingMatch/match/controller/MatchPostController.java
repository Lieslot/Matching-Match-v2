package com.matchingMatch.match.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.dto.MatchPostListElementResponse;
import com.matchingMatch.match.dto.MatchPostsResponse;
import com.matchingMatch.match.dto.ModifyMatchPostRequest;
import com.matchingMatch.match.dto.PostMatchPostRequest;
import com.matchingMatch.match.service.MatchPostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchPostController {

	private final MatchPostService matchPostService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public MatchPostsResponse getMatchPosts() {

		List<MatchPostListElementResponse> posts = matchPostService.getPosts();

		return new MatchPostsResponse(posts);
	}

	@AuthenticatedUser
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Long createNewMatchPost(
		@Valid @RequestBody PostMatchPostRequest matchPostRequest,
		@Authentication UserAuth userAuth) {

		return matchPostService.postNewMatch(matchPostRequest, userAuth.getId());
	}

	@GetMapping("/{postId}")
	@ResponseStatus(HttpStatus.OK)
	public Match getMatchPost(@PathVariable Long postId) {

		return matchPostService.getMatch(postId);
	}

	@AuthenticatedUser
	@DeleteMapping("/{postId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMatchPost(
		@PathVariable Long postId,
		@Authentication UserAuth userAuth) {

		matchPostService.deleteMatchPost(postId, userAuth.getId());
	}

	@AuthenticatedUser
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public void updateMatchPost(
		@Valid @RequestBody ModifyMatchPostRequest matchPostRequest,
		@Authentication UserAuth userAuth) {

		matchPostService.updateMatch(matchPostRequest, userAuth.getId());
	}

	@GetMapping("/host")
	@AuthenticatedUser
	@ResponseStatus(HttpStatus.OK)
	public MatchPostsResponse getMyMatchList(@Authentication UserAuth userAuth) {
		List<MatchPostListElementResponse> posts = matchPostService.getMyMatches(userAuth.getId());
		return new MatchPostsResponse(posts);
	}

	@GetMapping("/participate")
	@AuthenticatedUser
	@ResponseStatus(HttpStatus.OK)
	public MatchPostsResponse getOtherMatchList(@Authentication UserAuth userAuth) {
		List<MatchPostListElementResponse> posts = matchPostService.getOtherMatches(userAuth.getId());
		return new MatchPostsResponse(posts);
	}

	@GetMapping("/{teamId}/host")
	@AuthenticatedUser
	@ResponseStatus(HttpStatus.OK)
	public MatchPostsResponse getHostingMatches(@Authentication UserAuth userAuth, @PathVariable Long teamId) {
		List<MatchPostListElementResponse> posts = matchPostService.getHostingMatches(teamId);
		return new MatchPostsResponse(posts);
	}

}
