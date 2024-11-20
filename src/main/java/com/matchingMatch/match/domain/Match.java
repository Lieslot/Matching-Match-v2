package com.matchingMatch.match.domain;

import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.dto.MatchPostListElementResponse;
import com.matchingMatch.match.dto.ModifyMatchPostRequest;
import com.matchingMatch.match.exception.MatchAlreadyConfirmedException;
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

    private Boolean isParticipantRate;

    private Boolean isHostRate;

    @Builder
    public Match(Long id, Team host, Team participant, LocalDateTime startTime, LocalDateTime endTime, Gender gender, int stadiumCost,
                 String etc,
                 Stadium stadium, Boolean isParticipantRate, Boolean isHostRate) {
        this.id = id;
        this.host = host;
        this.participant = participant;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stadium = stadium;
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
            throw new MatchAlreadyConfirmedException(id);
        }
    }

    public void confirmMatch(Team team) {
        this.participant = team;
    }

    public void cancelMatch() {
        this.participant = null;
    }

    public void rateMannerPoint(MannerRate mannerRate) {
        if (mannerRate.isRater(host.getLeaderId())) {
            rateHost();
            host.rateMannerPoint(mannerRate.rate());
        } else if (mannerRate.isRater(participant.getLeaderId())) {
            rateParticipantRate();
            participant.rateMannerPoint(mannerRate.rate());
        } else {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }


    }

    public void rateHost() {
        isHostRate = true;
    }

    public void rateParticipantRate() {
        isParticipantRate = true;
    }

    public void setParticipantRate(Boolean participantRate) {
        isParticipantRate = participantRate;
    }

    public void setHostRate(Boolean hostRate) {
        isHostRate = hostRate;
    }

    public void update(ModifyMatchPostRequest newDetail) {

        this.startTime = newDetail.getStartTime();
        this.endTime = newDetail.getEndTime();
        this.stadiumCost = newDetail.getStadiumCost();
        this.etc = newDetail.getEtc();
        this.gender = newDetail.getGender();
    }



}
