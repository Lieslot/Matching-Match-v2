package com.matchingMatch.chat.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "chat_room_participant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private Long teamId;

    @Column(nullable = false)
    private Long lastChatId;

    @Builder
    public ChatRoomParticipantEntity(Long id, Long roomId, Long teamId, Long lastChatId) {
        this.id = id;
        this.roomId = roomId;
        this.teamId = teamId;
        this.lastChatId = lastChatId;
    }

}
