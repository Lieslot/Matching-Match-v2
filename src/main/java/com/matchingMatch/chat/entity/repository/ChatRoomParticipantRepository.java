package com.matchingMatch.chat.entity.repository;

import com.matchingMatch.chat.entity.ChatRoomParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomParticipantRepository extends JpaRepository<ChatRoomParticipantEntity, Long> {
    void deleteByRoomIdAndTeamId(Long roomId, Long teamId);

    List<ChatRoomParticipantEntity> findAllByTeamId(Long id);
}
