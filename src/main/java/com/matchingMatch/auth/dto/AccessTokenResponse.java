package com.matchingMatch.auth.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponse {

    @NotNull
    private String accessToken;
}
