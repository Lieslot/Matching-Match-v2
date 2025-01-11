package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ModifyMatchPostRequest {

    @NotNull
    private Long postId;

    @NotNull(message = "시작 시간을 선택해주세요")
    private LocalDateTime startTime;

    @NotNull(message = "종료 시간을 선택해주세요")
    private LocalDateTime endTime;

    @NotNull(message = "성별을 선택해주세요")
    private Gender gender;

    private int stadiumCost;

    @NotNull(message = "경기장 이름을 입력해주세요")
    private String etc;

}
