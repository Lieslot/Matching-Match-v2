package com.matchingMatch.chat.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchingMatch.chat.entity.ChatRoomParticipantEntity;

public interface ChatRoomParticipantRepository extends JpaRepository<ChatRoomParticipantEntity, Long> {
	void deleteByRoomIdAndTeamId(Long roomId, Long teamId);

	List<ChatRoomParticipantEntity> findAllByTeamId(Long id);
}
