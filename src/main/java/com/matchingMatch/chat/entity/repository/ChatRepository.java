package com.matchingMatch.chat.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchingMatch.chat.entity.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

	List<ChatEntity> findAllByRoomId(Long roomId);

}
