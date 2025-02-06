package com.matchingMatch.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.chat.dto.BlockChatUserRequest;
import com.matchingMatch.chat.dto.BlockedUsersResponse;
import com.matchingMatch.chat.service.ChatUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatUserController {

	private final ChatUserService chatUserService;

	// TODO 상대 차단
	@PostMapping("/block")
	@ResponseStatus(HttpStatus.CREATED)
	@AuthenticatedUser
	public void blockParticipant(@Authentication UserAuth userAuth,
		@Valid @RequestBody BlockChatUserRequest blockUseRequest) {

		chatUserService.blockUser(userAuth.getId(), blockUseRequest.getTeamId());

	}

	@PutMapping("/unblock")
	@ResponseStatus(HttpStatus.CREATED)
	@AuthenticatedUser
	public void unblockParticipant(@Authentication UserAuth userAuth,
		@Valid @RequestBody BlockChatUserRequest blockUseRequest) {

		chatUserService.unblockUser(userAuth.getId(), blockUseRequest.getTeamId());
	}

	@GetMapping("/block/users")
	@ResponseStatus(HttpStatus.OK)
	@AuthenticatedUser
	public BlockedUsersResponse getBlockList(@Authentication UserAuth userAuth) {
		return chatUserService.getBlockUsers(userAuth.getId());
	}

}
