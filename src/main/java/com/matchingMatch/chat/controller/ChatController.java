package com.matchingMatch.chat.controller;


import com.matchingMatch.auth.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    // TODO: 채팅 메세지 송신
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @AuthenticatedUser
    public void sendMessage() {

    }

    // TODO 채팅방 메세지 리스트
    @GetMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    @AuthenticatedUser
    public void getChatInRoom(@PathVariable Long roomId) {


    }


    // TODO: 채팅 내용 검색
    @GetMapping("/{roomId}/search")
    @ResponseStatus(HttpStatus.OK)
    @AuthenticatedUser
    public void searchChat(@PathVariable Long roomId, @RequestParam String keyword) {

    }


}
