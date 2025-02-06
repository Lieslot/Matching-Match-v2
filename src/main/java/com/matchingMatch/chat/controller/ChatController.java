package com.matchingMatch.chat.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.chat.dto.ChatDetail;
import com.matchingMatch.chat.dto.ChatDetailResponse;
import com.matchingMatch.chat.dto.SendChatResponse;
import com.matchingMatch.chat.dto.SendImageRequest;
import com.matchingMatch.chat.dto.SendMessageRequest;
import com.matchingMatch.chat.service.ChatService;
import com.matchingMatch.match.dto.TeamProfileResponse;
import com.matchingMatch.team.service.TeamService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

	private final ChatService chatService;

	private final TeamService teamService;

	// TODO: 채팅 메세지 송신
	@PostMapping("/message")
	@ResponseStatus(HttpStatus.CREATED)
	@AuthenticatedUser
	public SendChatResponse sendMessage(@Authentication UserAuth userAuth,
		@Valid @RequestBody SendMessageRequest sendChatRequest) {

		Long chatId = chatService.sendMessage(sendChatRequest, userAuth.getId());

		return new SendChatResponse(chatId);
	}

	// TODO 이미지 전송
	@PostMapping("/image")
	@ResponseStatus(HttpStatus.CREATED)
	@AuthenticatedUser
	public SendChatResponse sendImage(@Authentication UserAuth userAuth,
		@Valid @RequestBody SendImageRequest sendImageRequest,
		@RequestParam("file") MultipartFile image) {

		Long chatId = chatService.sendImage(sendImageRequest.getRoomId(),
			sendImageRequest.getTeamId(),
			sendImageRequest.getTargetTeamId(),
			image);

		return new SendChatResponse(chatId);
	}

	// TODO 채팅방 메세지 리스트
	@GetMapping("/{roomId}")
	@ResponseStatus(HttpStatus.OK)
	@AuthenticatedUser
	public ChatDetailResponse getChatInRoom(@PathVariable Long roomId, @Authentication UserAuth userAuth
		, @RequestParam Long targetTeamId, @RequestParam Long myTeamId) {
		TeamProfileResponse myTeam = teamService.getTeamProfile(myTeamId);
		TeamProfileResponse targetTeam = teamService.getTeamProfile(targetTeamId);
		List<ChatDetail> chatDetails = chatService.getChatInRoom(roomId);

		return ChatDetailResponse.builder()
			.roomId(roomId)
			.chatDetails(chatDetails)
			.myTeamLogoUrl(myTeam.getLogoUrl())
			.myTeamName(myTeam.getName())
			.targetTeamLogoUrl(targetTeam.getLogoUrl())
			.targetTeamName(targetTeam.getName())
			.build();
	}

	// TODO: 채팅 내용 검색
	@GetMapping("/{roomId}/search")
	@ResponseStatus(HttpStatus.OK)
	@AuthenticatedUser
	public void searchChat(@PathVariable Long roomId, @RequestParam String keyword) {

	}

}
