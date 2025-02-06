package com.matchingMatch.auth.service;

import com.matchingMatch.auth.AuthToken;
import com.matchingMatch.auth.JwtProvider;
import com.matchingMatch.auth.domain.RefreshToken;
import com.matchingMatch.auth.domain.RefreshTokenRepository;

import java.util.Optional;

import com.matchingMatch.auth.dto.SignUpRequest;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.user.domain.UserDetail;
import com.matchingMatch.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;


    @Transactional
    public AuthToken login(String username, String password) {


        UserDetail user = userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("아이디 또는 비밀번호가 잘못됨"));

        checkPassword(password, user);

        if (user.isBanned()) {
            throw new BadCredentialsException("차단된 계정입니다.");
        }

        String accessToken = jwtProvider.createAccessToken(user.getId());
        String refreshTokenContent = jwtProvider.createRefreshToken(user.getId());

        Optional<RefreshToken> tokenSearchResult = refreshTokenRepository.findById(user.getId());

        if (tokenSearchResult.isEmpty()) {
            refreshTokenRepository.save(new RefreshToken(user.getId(), refreshTokenContent));
        } else {
            RefreshToken refreshToken = tokenSearchResult.get();
            refreshToken.setContent(refreshTokenContent);
        }

        return new AuthToken(accessToken, refreshTokenContent);

    }

    private void checkPassword(String password, UserDetail userDetail) {

        if (!passwordEncoder.matches(password, userDetail.getPassword())) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 잘못됨");

        }
    }


    public void signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        userRepository.save(UserDetail.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .nickname(signUpRequest.getNickname())
                .role(Role.USER)
                .build());
    }


}
