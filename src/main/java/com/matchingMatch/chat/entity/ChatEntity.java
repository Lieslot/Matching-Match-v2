package com.matchingMatch.chat.entity;

import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "chat")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatType chatType;

    @Column(nullable = false)
    private Long sendTeamId;

    @Column(nullable = false)
    private String content;

    @Builder
    public ChatEntity(Long id, Long roomId, ChatType chatType, Long sendTeamId, String content) {
        this.id = id;
        this.roomId = roomId;
        this.chatType = chatType;
        this.sendTeamId = sendTeamId;
        this.content = content;
    }

}
