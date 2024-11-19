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

    private static final String INVALID_AUTHORITY = "권한이 없는 접근입니다.";


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

    private int stadiumCost;

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

    public void checkHost(Long leaderId) {
        if (!host.getLeaderId().equals(leaderId)) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }
    }

    public void checkHostOrParticipant(Long userId) {
        if (!host.getLeaderId().equals(userId) && !participant.getLeaderId().equals(userId)) {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }
    }

    public void checkAlreadyConfirmed() {
        if (participant != null) {
            throw new IllegalArgumentException("이미 참가자가 확정된 매치입니다.");
        }
    }

    public void confirmParticipant(Team team) {
        this.participant = team;
    }

    public void cancelParticipant() {
        this.participant = null;
    }

}
