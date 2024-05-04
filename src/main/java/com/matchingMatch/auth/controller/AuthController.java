package com.matchingMatch.auth.controller;


import com.matchingMatch.auth.dto.AccessTokenResponse;
import com.matchingMatch.auth.dto.LoginRequest;
import com.matchingMatch.auth.service.AuthService;
import com.matchingMatch.match.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String accessToken = authService.login(username, password);

        return ResponseEntity.status(HttpStatus.CREATED).body(new AccessTokenResponse(accessToken));
    }






}
