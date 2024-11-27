package com.matchingMatch.chat.entity;

import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity(name = "block_chat_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE block_chat_user SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class BlockChatUserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long blockUserId;

    private LocalDateTime deletedAt;

    @Builder
    public BlockChatUserEntity(Long id, Long userId, Long blockUserId) {
        this.userId = userId;
        this.blockUserId = blockUserId;
    }

    public void unblock() {
        deletedAt = null;
    }



}
