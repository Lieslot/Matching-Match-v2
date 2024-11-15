package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.enums.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MatchPostListElementResponse {
    private Long id;

    private String hostName;

    private String participantName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Gender gender;

    private String stadiumName;

    private String stadiumAddress;

    @Builder
    public MatchPostListElementResponse(Long id, String hostName, String participantName, LocalDateTime startTime, LocalDateTime endTime, Gender gender, String stadiumName, String stadiumAddress) {
        this.id = id;
        this.hostName = hostName;
        this.participantName = participantName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gender = gender;
        this.stadiumName = stadiumName;
        this.stadiumAddress = stadiumAddress;
    }

}
