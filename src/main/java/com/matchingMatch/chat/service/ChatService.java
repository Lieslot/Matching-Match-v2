package com.matchingMatch.chat.service;

import com.matchingMatch.chat.FileStorage;
import com.matchingMatch.chat.dto.SendChatRequest;
import com.matchingMatch.chat.dto.SendChatResponse;
import com.matchingMatch.chat.implement.ChatAdapter;
import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final FileStorage fileStorage;
    private final TeamAdapter teamAdapter;
    private final ChatAdapter chatAdapter;

    @Transactional
    public SendChatResponse sendMessage(SendChatRequest sendChatRequest, Long userId) {
        Team userTeam = teamAdapter.getTeamByLeaderId(userId);

        Long chatId = chatAdapter.writeMessage(userTeam.getId(),
                sendChatRequest.getRoomId(),
                sendChatRequest.getContent(),
                sendChatRequest.getTargetTeamId());

        // TODO 상대방에게 알림 보내기
        return new SendChatResponse(chatId);
    }


    public void sendImage() {

    }
}
