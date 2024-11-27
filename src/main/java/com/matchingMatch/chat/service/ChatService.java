package com.matchingMatch.chat.service;

import com.matchingMatch.chat.entity.ChatType;
import com.matchingMatch.chat.file.FileStorage;
import com.matchingMatch.chat.dto.SendMessageRequest;
import com.matchingMatch.chat.dto.SendChatResponse;
import com.matchingMatch.chat.implement.ChatAdapter;
import com.matchingMatch.match.TeamAdapter;
import com.matchingMatch.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final FileStorage fileStorage;
    private final TeamAdapter teamAdapter;
    private final ChatAdapter chatAdapter;

    @Transactional
    public Long sendMessage(SendMessageRequest sendChatRequest, Long userId) {
        Team userTeam = teamAdapter.getTeamByLeaderId(userId);
        // TODO 차단 로직 추가

        // TODO 상대방에게 알림 보내기
        return chatAdapter.write(userTeam.getId(),
                sendChatRequest.getRoomId(),
                sendChatRequest.getContent(),
                ChatType.MESSAGE,
                sendChatRequest.getTargetTeamId()

        );
    }


    public Long sendImage(Long roomId, Long teamId, Long targetTeamId, MultipartFile multipartFile) {

        String uploadedUrl = fileStorage.uploadFile(multipartFile);

        return chatAdapter.write(teamId, roomId, uploadedUrl, ChatType.IMAGE, targetTeamId);
    }
}
