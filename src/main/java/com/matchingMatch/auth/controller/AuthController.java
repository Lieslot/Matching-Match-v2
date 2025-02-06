package com.matchingMatch.auth.controller;


import com.matchingMatch.auth.dto.AccessTokenResponse;
import com.matchingMatch.auth.dto.LoginRequest;
import com.matchingMatch.auth.dto.SignUpRequest;
import com.matchingMatch.auth.service.AuthService;
import com.matchingMatch.auth.AuthToken;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse>  login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        try {
            AuthToken authToken = authService.login(username, password);
            ResponseCookie cookie = ResponseCookie.from("refresh_token", authToken.getRefreshToken())
                    .httpOnly(true)
                    .secure(true)
                    .maxAge(7654321L)
                    .path("/")
                    .sameSite("None")
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());

            return ResponseEntity.ok(new AccessTokenResponse(authToken.getAccessToken()));
        } catch (Exception e) {
            log.error("로그인 실패", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }

    // TODO 회원가입 기능 추가

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody SignUpRequest signUpRequest) {

        authService.signUp(signUpRequest);
    }

}
