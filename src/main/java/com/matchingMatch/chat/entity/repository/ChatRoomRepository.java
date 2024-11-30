package com.matchingMatch.chat.entity.repository;

import com.matchingMatch.chat.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {


}
