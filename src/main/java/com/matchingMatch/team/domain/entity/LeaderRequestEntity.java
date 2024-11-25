package com.matchingMatch.team.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "leader_request")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LeaderRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long teamId;

    private Long sendUserId;

    private Long targetUserId;


    public boolean hasTargetUserId(Long targetUserId) {
        return this.targetUserId.equals(targetUserId);
    }

    @Builder
    public LeaderRequestEntity(Long id, Long teamId, Long sendUserId, Long targetUserId) {
        this.id = id;
        this.teamId = teamId;
        this.sendUserId = sendUserId;
        this.targetUserId = targetUserId;
    }

}
