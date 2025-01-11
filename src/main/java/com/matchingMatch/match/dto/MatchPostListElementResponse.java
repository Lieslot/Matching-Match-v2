package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MatchPostListElementResponse {

    @NotNull
    private Long id;
    @NotNull
    private String hostName;
    @NotNull
    private String participantName;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    @NotNull
    private Gender gender;
    @NotNull
    private String stadiumName;
    @NotNull
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
