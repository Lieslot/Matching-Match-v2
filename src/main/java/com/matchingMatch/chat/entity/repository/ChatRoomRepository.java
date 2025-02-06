package com.matchingMatch.chat.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchingMatch.chat.entity.ChatRoomEntity;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

}
