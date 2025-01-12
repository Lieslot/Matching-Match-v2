package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ModifyMatchPostRequest {

    @NotNull
    private Long postId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Gender gender;

    private Integer stadiumCost;

    private String etc;

}
