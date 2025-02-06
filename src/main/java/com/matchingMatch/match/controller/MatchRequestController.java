package com.matchingMatch.match.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.dto.MatchRequestDetail;
import com.matchingMatch.match.dto.MatchRequestThumbnail;
import com.matchingMatch.match.service.MatchingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchRequestController {

	private final MatchingService matchingService;

	// TODO 다른 사람이 신청한 매치 요청 목록 보기
	@GetMapping("/requests")
	@ResponseStatus(HttpStatus.OK)
	@AuthenticatedUser
	public List<MatchRequestThumbnail> getReceivedRequests(@Authentication UserAuth userAuth) {

		return matchingService.getRequestThumbnail(userAuth.getId());
	}

	@GetMapping("/requests/{matchId}/detail")
	@ResponseStatus(HttpStatus.OK)
	@AuthenticatedUser
	public MatchRequestDetail getReceivedRequestsDetail(@PathVariable Long matchId, @Authentication UserAuth userAuth) {

		return matchingService.getRequestDetail(matchId);
	}

}
