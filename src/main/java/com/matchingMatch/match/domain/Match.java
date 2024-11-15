package com.matchingMatch.match.domain;

import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.dto.MatchPostListElementResponse;
import com.matchingMatch.team.domain.entity.Team;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class Match {

    private Long id;

    @NotNull
    private Team host;

    private Team participant;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd:hh:mm:ss")
    private LocalDateTime startTime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd:hh:mm:ss")
    private LocalDateTime endTime;

    @NotNull
    private Gender gender;

    private Stadium stadium;

    private String etc;

    @Builder
    public Match(Long id, Team host, Team participant, LocalDateTime startTime, LocalDateTime endTime, Gender gender, int stadiumCost,
                 String etc,
                 Stadium stadium) {
        this.id = id;
        this.host = host;
        this.participant = participant;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stadium = stadium;
    }


    public void checkHostEqualTo(Long hostId) {
        if (this.host.getId().equals(hostId)) {
            throw new IllegalArgumentException();
        }
    }

    public MatchPostListElementResponse toMatchPostResponse() {
        return MatchPostListElementResponse.builder()
                .id(id)
                .hostName(host.getName())
                .participantName(participant.getName())
                .startTime(startTime)
                .endTime(endTime).build();
    }


}
