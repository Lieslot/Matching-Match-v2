package com.matchingMatch.auth.controller;


import com.matchingMatch.auth.dto.AccessTokenResponse;
import com.matchingMatch.auth.dto.LoginRequest;
import com.matchingMatch.auth.service.AuthService;
import com.matchingMatch.auth.AuthToken;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AccessTokenResponse> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        AuthToken authToken = authService.login(username, password);

        ResponseCookie cookie = ResponseCookie.from("refresh_token", authToken.getRefreshToken())
                                             .httpOnly(true)
                                             .secure(true)
                                             .maxAge(7654321L)
                                             .path("/")
                                             .sameSite("None")
                                             .build();

       response.addHeader("Set-Cookie", cookie.toString());

       return ResponseEntity.status(HttpStatus.CREATED)
                             .body(new AccessTokenResponse(authToken.getAccessToken()));
    }


    // TODO 회원가입 기능 추가

}
