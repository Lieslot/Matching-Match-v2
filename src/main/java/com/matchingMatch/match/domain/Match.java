package com.matchingMatch.match.domain;

import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.dto.ModifyMatchPostRequest;
import com.matchingMatch.match.exception.MatchAlreadyConfirmedException;
import com.matchingMatch.team.domain.entity.Team;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.util.Objects.requireNonNullElse;

@Getter
public class Match {


    private static final String INVALID_AUTHORITY = "권한이 없는 접근입니다.";


    private Long id;

    @NotNull
    private Long hostId;

    private Long participantId;

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

    private Long stadiumId;

    private Integer stadiumCost;

    private String etc;

    private Boolean isParticipantRate = false;

    private Boolean isHostRate = false;

    @Builder
    public Match(Long id, Long hostId, Long participantId, LocalDateTime startTime, LocalDateTime endTime, Gender gender, int stadiumCost,
                 String etc,
                 Long stadiumId, boolean isParticipantRate, boolean isHostRate, LocalDateTime confirmedTime) {
        this.id = id;
        this.hostId = hostId;
        this.participantId = participantId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stadiumId = stadiumId;
        this.stadiumCost = stadiumCost;
        this.gender = gender;
        this.etc = etc;
        this.confirmedTime = confirmedTime;
        this.isParticipantRate = isParticipantRate;
        this.isHostRate = isHostRate;
    }

    public void checkAlreadyConfirmed() {
        if (participantId != null) {
            throw new MatchAlreadyConfirmedException(id);
        }
    }

    public void confirmMatch(Team team) {
            this.participantId = team.getId();
            this.confirmedTime = LocalDateTime.now();
    }

    public void cancelMatch() {
        this.participantId = null;
        this.confirmedTime = null;
    }

    public void checkCancelDeadline(LocalDateTime now) {
        LocalDateTime deadline = this.confirmedTime.plusMinutes(10);

        if (now.isBefore(deadline)) {
            long minute = ChronoUnit.MINUTES.between(now, deadline);
            long second = ChronoUnit.SECONDS.between(now, deadline);

            throw new IllegalArgumentException(String.format("%d분 %d초 후에 취소 가능합니다.", minute, second));
        }
    }

    public Boolean isConfirmed() {
        return participantId != null;
    }

    public Boolean started() {
        return startTime.isBefore(LocalDateTime.now());
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

        this.startTime = requireNonNullElse(newDetail.getStartTime(), this.startTime);
        this.endTime = requireNonNullElse(newDetail.getEndTime(), this.endTime);
        this.stadiumCost = requireNonNullElse(newDetail.getStadiumCost(), this.stadiumCost);
        this.etc = requireNonNullElse(newDetail.getEtc(), this.etc);
        this.gender = requireNonNullElse(newDetail.getGender(), this.gender);
    }

    public Boolean isEnd() {
        return endTime.isBefore(LocalDateTime.now());
    }

}
