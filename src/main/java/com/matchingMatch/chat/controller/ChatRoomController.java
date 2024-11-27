package com.matchingMatch.chat.controller;


import com.matchingMatch.auth.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat/room")
public class ChatRoomController {


    // TODO 채팅방 preview 리스트
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @AuthenticatedUser
    public void getChatRoomPreview() {

    }

    // TODO 채팅방 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @AuthenticatedUser
    public void createChatRoom() {

    }

    // TODO 채팅방 퇴장
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @AuthenticatedUser
    public void exitChatRoom() {
        // ChatRoomParticipantEntity 삭제 하는 방식으로 구현
    }

    // TODO 상대 차단
    @PostMapping("/block")
    @ResponseStatus(HttpStatus.CREATED)
    @AuthenticatedUser
    public void blockParticipant() {

    }

    // TODO 채팅방 검색
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @AuthenticatedUser
    public void searchChatRoom() {

    }



}
