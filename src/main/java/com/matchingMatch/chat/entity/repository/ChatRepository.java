package com.matchingMatch.chat.entity.repository;

import com.matchingMatch.chat.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {


}
