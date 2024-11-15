package com.matchingMatch.auth.service;

import com.matchingMatch.auth.AuthToken;
import com.matchingMatch.auth.JwtProvider;
import com.matchingMatch.auth.domain.RefreshToken;
import com.matchingMatch.auth.domain.RefreshTokenRepository;

import java.util.Optional;

import com.matchingMatch.user.domain.UserDetail;
import com.matchingMatch.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;


    @Transactional
    public AuthToken login(String username, String password) {

        UserDetail user = userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("아이디 또는 비밀번호가 잘못됨"));

        if (!checkPassword(password, user)) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 잘못됨");
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

    private boolean checkPassword(String password, UserDetail userDetail) {

        return passwordEncoder.matches(password, userDetail.getPassword());
    }


}
