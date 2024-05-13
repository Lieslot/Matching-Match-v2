package com.matchingMatch.auth.service;

import com.matchingMatch.auth.AuthToken;
import com.matchingMatch.auth.JwtProvider;
import com.matchingMatch.auth.domain.RefreshToken;
import com.matchingMatch.auth.domain.RefreshTokenRepository;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.repository.TeamRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;


    @Transactional
    public AuthToken login(String username, String password) {

        Optional<Team> result = teamRepository.findByAccount(username);

        if (result.isEmpty()) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 잘못됨");
        }
        Team team = result.get();

        if (!checkPassword(password, team)) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 잘못됨");
        }

        String accessToken = jwtProvider.createAccessToken(team.getId());
        String refreshTokenContent = jwtProvider.createRefreshToken(team.getId());

        Optional<RefreshToken> tokenSearchResult = refreshTokenRepository.findById(team.getId());

        if (tokenSearchResult.isEmpty()) {
            refreshTokenRepository.save(new RefreshToken(team.getId(), refreshTokenContent));
        } else {
            RefreshToken refreshToken = tokenSearchResult.get();
            refreshToken.setContent(refreshTokenContent);
        }

        return new AuthToken(accessToken, refreshTokenContent);

    }

    private boolean checkPassword(String password, Team team) {

        return passwordEncoder.matches(password, team.getPassword());
    }


}
