package com.matchingMatch.chat.controller;


import com.matchingMatch.auth.AuthenticatedUser;
import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.chat.dto.BlockChatUserRequest;
import com.matchingMatch.chat.dto.BlockedUsersResponse;
import com.matchingMatch.chat.dto.ExistChatRoomRequest;
import com.matchingMatch.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat/room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;


    // TODO 채팅방 preview 리스트
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @AuthenticatedUser
    public void getChatRoomPreview() {

    }

    // TODO 채팅방 퇴장
    @PostMapping("/exit")
    @ResponseStatus(HttpStatus.OK)
    @AuthenticatedUser
    public void exitChatRoom(@RequestBody ExistChatRoomRequest existChatRoomRequest, @Authentication UserAuth userAuth) {
        // ChatRoomParticipantEntity 삭제 하는 방식으로 구현

        chatRoomService.exitChatRoom(existChatRoomRequest.getRoomId(), existChatRoomRequest.getTeamId());
    }

    // TODO 상대 차단
    @PostMapping("/block")
    @ResponseStatus(HttpStatus.CREATED)
    @AuthenticatedUser
    public void blockParticipant(@Authentication UserAuth userAuth, @RequestBody BlockChatUserRequest blockUseRequest) {

        chatRoomService.blockUser(userAuth.getId(), blockUseRequest.getTeamId());

    }

    @PutMapping("/unblock")
    @ResponseStatus(HttpStatus.CREATED)
    @AuthenticatedUser
    public void unblockParticipant(@Authentication UserAuth userAuth, @RequestBody BlockChatUserRequest blockUseRequest) {

        chatRoomService.unblockUser(userAuth.getId(), blockUseRequest.getTeamId());
    }

    @GetMapping("/block/users")
    @ResponseStatus(HttpStatus.OK)
    @AuthenticatedUser
    public BlockedUsersResponse getBlockList(@Authentication UserAuth userAuth) {
        return chatRoomService.getBlockUsers(userAuth.getId());
    }

    // TODO 채팅방 검색
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @AuthenticatedUser
    public void searchChatRoom() {

    }



}
