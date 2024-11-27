package com.matchingMatch.chat.entity.repository;

import com.matchingMatch.chat.entity.ChatRoomParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomParticipantRepository extends JpaRepository<ChatRoomParticipantEntity, Long> {
}
