package com.matchingMatch.chat.service;

import com.matchingMatch.chat.dto.ChatRoomPreview;
import com.matchingMatch.chat.dto.GetChatRoomPreviewResponse;
import com.matchingMatch.chat.entity.ChatEntity;
import com.matchingMatch.chat.entity.ChatRoomEntity;
import com.matchingMatch.chat.entity.ChatRoomParticipantEntity;
import com.matchingMatch.chat.entity.repository.ChatRepository;
import com.matchingMatch.chat.entity.repository.ChatRoomParticipantRepository;
import com.matchingMatch.chat.entity.repository.ChatRoomRepository;
import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.team.domain.entity.Team;
import com.matchingMatch.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;
    private final UserRepository userRepository;
    private final TeamAdapter teamAdapter;
    private final ChatRepository chatRepository;

    public void exitChatRoom(Long roomId, Long teamId) {

        chatRoomParticipantRepository.deleteByRoomIdAndTeamId(roomId, teamId);

    }

    // TODO 추후에 벌크연산을 사용하여 최적화
    public GetChatRoomPreviewResponse getChatRoomPreview(Long userId) {
        Team userTeam = teamAdapter.getTeamByLeaderId(userId);

        List<ChatRoomParticipantEntity> participantEntities = chatRoomParticipantRepository.findAllByTeamId(userTeam.getId());

        // 룸 아이디로 채팅방 정보 가져오기
        List<Long> chatRoomIds = participantEntities.stream()
                .map(ChatRoomParticipantEntity::getRoomId)
                .toList();

        List<ChatRoomEntity> rooms = chatRoomRepository.findAllById(chatRoomIds);

        List<ChatRoomPreview> previews = rooms.stream()
                .map(room -> {
                    ChatEntity chat = chatRepository.findById(room.getLastChatId()).orElse(null);
                    Team team = teamAdapter.getTeamBy(chat.getSendTeamId());
                    return ChatRoomPreview.builder()
                            .roomId(room.getId())
                            .teamLogoUrl(team.getLogoUrl())
                            .teamName(team.getName())
                            .lastChat(chat.getContent())
                            .lastChatTime(chat.getCreatedAt())
                            .build();
                }).toList();

        return new GetChatRoomPreviewResponse(previews);

    }
}

