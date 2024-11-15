package com.matchingMatch.match.domain.entity;


import com.matchingMatch.match.domain.BaseEntity;
import com.matchingMatch.match.domain.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "match")
@Getter
@Setter
public class MatchEntity extends BaseEntity {

    private static final String INVALID_AUTHORITY = "권한이 없는 접근입니다.";

    public MatchEntity() {

    }

    @Builder
    public MatchEntity(Long hostId, Long participantId, LocalDateTime startTime, LocalDateTime endTime, Gender gender,
                       int stadiumCost, String etc) {
        this.hostId = hostId;
        this.participantId = participantId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gender = gender;
        this.stadiumCost = stadiumCost;
        this.etc = etc;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long hostId;

    private Long participantId;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd:hh:mm:ss")
    private LocalDateTime startTime;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd:hh:mm:ss")
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Gender gender;

    private int stadiumCost;

    private String etc;



    public void checkHostEqualTo(Long target) {

        if (!hostId.equals(target)) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

    }

    public void setHost(Long target) {
        this.hostId = target;
    }

    public void setParticipant(Long target) {
        this.participantId = target;
    }

    public void isHost(Long target) {
        if (!hostId.equals(target)) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }
    }

    public void isParticipant(Long target) {
        if (!participantId.equals(target)) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }
    }

    public void checkParticipantEqualTo(Long target) {

        if (!participantId.equals(target)) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

    }

    public void checkInvolvedInMatch(Long target) {
        if (!hostId.equals(target) && !participantId.equals(target)) {

            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }

    }
}
