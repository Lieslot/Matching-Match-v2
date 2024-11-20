package com.matchingMatch.match.domain.entity;


import com.matchingMatch.match.domain.BaseEntity;
import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
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

    public MatchEntity() {

    }

    public static MatchEntity from(Match match) {
        return MatchEntity.builder()
                .hostId(match.getHost().getId())
                .participantId(match.getParticipant().getId())
                .startTime(match.getStartTime())
                .endTime(match.getEndTime())
                .stadiumCost(match.getStadiumCost())
                .stadiumId(match.getStadium().getId())
                .build();
    }

    @Builder
    public MatchEntity(Long hostId, Long participantId, LocalDateTime startTime, LocalDateTime endTime, Gender gender,
                       int stadiumCost, Long stadiumId, String etc) {
        this.hostId = hostId;
        this.participantId = participantId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gender = gender;
        this.stadiumCost = stadiumCost;
        this.etc = etc;
        this.stadiumId = stadiumId;
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

    private Long stadiumId;

    private int stadiumCost;

    private String etc;


    public void setHost(Long target) {
        this.hostId = target;
    }


    public void isHost(Long target) {
        if (!hostId.equals(target)) {
            throw new UnauthorizedAccessException();
        }
    }


}
