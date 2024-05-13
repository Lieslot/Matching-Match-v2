package com.matchingMatch.auth;


import static org.mockito.ArgumentMatchers.any;

import com.matchingMatch.auth.config.SecurityConfig;
import com.matchingMatch.auth.domain.RefreshToken;
import com.matchingMatch.auth.domain.RefreshTokenRepository;
import com.matchingMatch.auth.service.AuthService;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.enums.Gender;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.match.domain.repository.TeamRepository;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class AuthServiceTest {

    private static final String PASSWORD = "password1234";
    private static final String ACCOUNT = "rlaxodud";

    @Autowired
    private AuthService authService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private Team team;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @BeforeEach
    void setUp() {
        team = Team.builder()
                .teamName("wkeldw FC")
                .gender(Gender.FEMALE)
                .teamDescription("ddd")
                .account(ACCOUNT)
                .password(passwordEncoder.encode(PASSWORD))
                .role(Role.USER)
                .region("서울 관악구")
                .build();

        teamRepository.save(team);

    }

    @AfterEach
    void clear() {
        teamRepository.deleteAll();
    }


    @Transactional
    @Test
    void 로그인_테스트() {
        AuthToken authToken = authService.login(ACCOUNT, PASSWORD);

        Assertions.assertThatNoException().isThrownBy(
                () -> jwtProvider.validateToken(authToken.getAccessToken(), authToken.getRefreshToken())
        );

    }

    @Transactional
    @Test
    void 기존에_리프레쉬_토큰이_생성된_아이디에_로그인_시_리프레쉬_토큰이_갱신되는지_테스트() throws InterruptedException {

        // given
        String before = refreshTokenRepository.save(
                new RefreshToken(team.getId(), jwtProvider.createRefreshToken(team.getId()))).getContent();
        Thread.sleep(1000);
        // when
        String after = authService.login(ACCOUNT, PASSWORD).getRefreshToken();
        // then

        Assertions.assertThat(before).isNotEqualTo(after);

    }

    @Transactional
    @Test
    void 잘못된_유저_정보_입력_시_에러_발생_테스트() {

        Assertions.assertThatThrownBy(() -> authService.login(ACCOUNT, "12345670923ddddd"));
        Assertions.assertThatThrownBy(() -> authService.login("dksdklwqldqe", PASSWORD));

    }

}
