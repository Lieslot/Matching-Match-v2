package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.enums.Gender;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchPostResponse {

    @NotNull(message = "잘못된 접근입니다.")
    private Team hostId;

    private Team participantId;

    @NotNull(message = "시작 시간을 선택해주세요")
    private LocalDateTime startTime;

    @NotNull(message = "종료 시간을 선택해주세요")
    private LocalDateTime endTime;

    @NotNull(message = "성별을 선택해주세요")
    private Gender gender;

    private int stadiumCost;

    private String etc;


}
