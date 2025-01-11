package com.matchingMatch.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserUnbanRequest {

    @NotNull
    private Long id;
}
