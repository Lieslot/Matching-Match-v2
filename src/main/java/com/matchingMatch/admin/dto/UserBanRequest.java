package com.matchingMatch.admin.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserBanRequest {

    @NotNull(message = "유저 아이디는 필수입니다.")
    private Long id;
    @NotNull(message = "밴 기간은 필수입니다.")
    private Integer day;

}
