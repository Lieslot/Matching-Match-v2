package com.matchingMatch.auth.service;

import com.matchingMatch.auth.JwtProvider;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.repository.TeamRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;
    private final JwtProvider jwtProvider;

    public String login(String username, String password) {

        Optional<Team> result = teamRepository.findByAccount(username);

        if (result.isEmpty()) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 잘못됨");
        }
        Team team = result.get();
        String encoded = passwordEncoder.encode(password);
        if (!encoded.equals(team.getPassword())) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 잘못됨");
        }

        return jwtProvider.createAccessToken(team.getId());

    }






}
