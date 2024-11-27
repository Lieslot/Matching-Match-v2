package com.matchingMatch.chat.service;

import com.matchingMatch.chat.FileStorage;
import com.matchingMatch.chat.dto.SendChatRequest;
import com.matchingMatch.chat.dto.SendChatResponse;
import com.matchingMatch.chat.entity.ChatEntity;
import com.matchingMatch.chat.entity.ChatRoomEntity;
import com.matchingMatch.chat.entity.ChatRoomParticipantEntity;
import com.matchingMatch.chat.entity.ChatType;
import com.matchingMatch.chat.entity.repository.ChatRepository;
import com.matchingMatch.chat.entity.repository.ChatRoomParticipantRepository;
import com.matchingMatch.chat.entity.repository.ChatRoomRepository;
import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final FileStorage fileStorage;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;
    private final TeamAdapter teamAdapter;

    @Transactional
    public SendChatResponse sendMessage(SendChatRequest sendChatRequest, Long userId) {
        Team userTeam = teamAdapter.getTeamByLeaderId(userId);

        if (!sendChatRequest.hasRoomId()) {
            ChatRoomEntity room = chatRoomRepository.save(new ChatRoomEntity());

            ChatEntity chat = chatRepository.save(ChatEntity.builder()
                    .roomId(room.getId())
                    .chatType(ChatType.MESSAGE)
                    .content(sendChatRequest.getContent())
                    .sendTeamId(userTeam.getId())
                    .build());

            chatRoomParticipantRepository.save(ChatRoomParticipantEntity.builder()
                    .roomId(room.getId())
                    .teamId(userTeam.getId())
                            .lastChatId(chat.getId())
                    .build());

            return new SendChatResponse(chat.getId());
        }

        ChatEntity chat = chatRepository.save(ChatEntity.builder()
                .roomId(sendChatRequest.getRoomId())
                .chatType(ChatType.MESSAGE)
                .content(sendChatRequest.getContent())
                .sendTeamId(userTeam.getId())
                .build());



        // TODO 상대방에게 알림 보내기
        return new SendChatResponse(chat.getId());
    }


    public void sendImage() {

    }
}
