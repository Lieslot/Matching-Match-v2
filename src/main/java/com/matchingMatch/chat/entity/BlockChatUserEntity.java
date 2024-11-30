package com.matchingMatch.chat.entity;

import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Column;
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

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private Long blockUserId;

    @Column(nullable = false)
    private Long blockTeamId;

    private LocalDateTime deletedAt;

    @Builder
    public BlockChatUserEntity(Long id, Long userId, Long blockUserId, Long blockTeamId) {
        this.id = id;
        this.userId = userId;
        this.blockUserId = blockUserId;
        this.blockTeamId = blockTeamId;
    }

    public void unblock() {
        deletedAt = null;
    }



}
