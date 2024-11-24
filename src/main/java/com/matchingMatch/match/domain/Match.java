package com.matchingMatch.match.domain;

import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.dto.MatchPostListElementResponse;
import com.matchingMatch.match.dto.ModifyMatchPostRequest;
import com.matchingMatch.match.exception.MatchAlreadyConfirmedException;
import com.matchingMatch.match.exception.MatchAlreadyRatedException;
import com.matchingMatch.match.exception.UnauthorizedAccessException;
import com.matchingMatch.team.domain.entity.Team;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd:hh:mm:ss")
    private LocalDateTime confirmedTime;

    @NotNull
    private Gender gender;

    private Stadium stadium;

    private int stadiumCost;

    private String etc;

    private Boolean isParticipantRate = false;

    private Boolean isHostRate = false;

    @Builder
    public Match(Long id, Team host, Team participant, LocalDateTime startTime, LocalDateTime endTime, Gender gender, int stadiumCost,
                 String etc,
                 Stadium stadium, Boolean isParticipantRate, Boolean isHostRate, LocalDateTime confirmedTime) {
        this.id = id;
        this.host = host;
        this.participant = participant;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stadium = stadium;
        this.gender = gender;
        this.etc = etc;
        this.confirmedTime = confirmedTime;
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
            throw new UnauthorizedAccessException();
        }
    }

    public void checkHostOrParticipant(Long userId) {
        if (!host.getLeaderId().equals(userId) && !participant.getLeaderId().equals(userId)) {
            throw new UnauthorizedAccessException();
        }
    }

    public void checkAlreadyConfirmed() {
        if (participant != null) {
            throw new MatchAlreadyConfirmedException(id);
        }
    }

    public void confirmMatch(Team team) {
        this.participant = team;
        this.confirmedTime = LocalDateTime.now();
    }

    public void cancelMatch() {
        this.participant = null;
        this.confirmedTime = null;
    }

    public void checkCancelDeadline(LocalDateTime now) {
        LocalDateTime deadline = this.confirmedTime.plusMinutes(10);

        if (now.isBefore(deadline)) {
            long minute = ChronoUnit.MINUTES.between(now, deadline);
            long second = ChronoUnit.SECONDS.between(now, deadline);

            throw new IllegalArgumentException(String.format("%d분 %초 후에 취소 가능합니다.", minute, second));
        }
    }

    public void rateMannerPoint(MannerRate mannerRate) {
        if (mannerRate.isRater(participant.getLeaderId())) {
            rateParticipantRate();
            host.rateMannerPoint(mannerRate.rate());
        } else if (mannerRate.isRater(host.getLeaderId())) {
            rateHost();
            participant.rateMannerPoint(mannerRate.rate());
        } else {
            throw new IllegalArgumentException(INVALID_AUTHORITY);
        }


    }

    private void rateHost() {
        isHostRate = true;
    }

    private void rateParticipantRate() {
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


    public void checkAlreadyRate(Long userId) {

        if (isHostRate && host.getLeaderId().equals(userId)) {
            throw new MatchAlreadyRatedException(host.getLeaderId());
        }

        if (isParticipantRate && participant.getLeaderId().equals(userId)) {
            throw new MatchAlreadyRatedException(participant.getLeaderId());
        }
    }
}
