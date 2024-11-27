package com.matchingMatch.chat.implement;

import com.matchingMatch.chat.entity.ChatEntity;
import com.matchingMatch.chat.entity.ChatRoomEntity;
import com.matchingMatch.chat.entity.ChatRoomParticipantEntity;
import com.matchingMatch.chat.entity.ChatType;
import com.matchingMatch.chat.entity.repository.ChatRepository;
import com.matchingMatch.chat.entity.repository.ChatRoomParticipantRepository;
import com.matchingMatch.chat.entity.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatAdapter {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;


    public Long write(Long teamId, Long roomId, String content, ChatType chatType, Long targetTeamId) {

        if (roomId == -1L) {

            ChatRoomEntity room = chatRoomRepository.save(new ChatRoomEntity());

            ChatEntity chat = chatRepository.save(ChatEntity.builder()
                    .roomId(room.getId())
                    .chatType(chatType)
                    .content(content)
                    .sendTeamId(teamId)
                    .build());

            chatRoomParticipantRepository.save(ChatRoomParticipantEntity.builder()
                    .roomId(room.getId())
                    .teamId(teamId)
                    .lastChatId(chat.getId())
                    .build());


            chatRoomParticipantRepository.save(ChatRoomParticipantEntity.builder()
                    .roomId(room.getId())
                    .teamId(targetTeamId)
                    .lastChatId(chat.getId())
                    .build());

            return chat.getId();
        }

        return chatRepository.save(ChatEntity.builder()
                .roomId(roomId)
                .chatType(chatType)
                .content(content)
                .sendTeamId(teamId)
                .build()).getId();
    }

}
