package com.matchingMatch.chat.entity.repository;


import com.matchingMatch.chat.entity.BlockChatUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockUserRepository extends JpaRepository<BlockChatUserEntity, Long> {
    void deleteByUserIdAndBlockUserId(Long userId, Long blockUserId);

    List<BlockChatUserEntity> findAllByUserId(Long userId);

    Optional<BlockChatUserEntity> findByUserIdAndBlockUserId(Long userId, Long blockUserId);
}
