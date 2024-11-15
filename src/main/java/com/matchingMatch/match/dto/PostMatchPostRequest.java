package com.matchingMatch.match.dto;

import com.matchingMatch.match.domain.Match;
import com.matchingMatch.match.domain.enums.Gender;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostMatchPostRequest {

    @NotNull
    private Long hostId;

    @NotNull
    private Long postId;

    @NotNull(message = "시작 시간을 선택해주세요")
    private LocalDateTime startTime;

    @NotNull(message = "종료 시간을 선택해주세요")
    private LocalDateTime endTime;

    @NotNull(message = "성별을 선택해주세요")
    private Gender gender;

    private int stadiumCost;

    private String etc;


    public Match toEntity() {
        return Match.builder()
                    .hostId(hostId)
                    .startTime(startTime)
                    .endTime(endTime)
                    .gender(gender)
                    .stadiumCost(stadiumCost)
                    .etc(etc)
                    .build();
    }


}
