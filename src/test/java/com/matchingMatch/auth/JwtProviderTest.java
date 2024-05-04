package com.matchingMatch.auth;

import com.matchingMatch.auth.service.AuthService;
import com.matchingMatch.match.domain.repository.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
public class JwtProviderTest {


    @Autowired
    private JwtProvider jwtProvider;


    @Test
    void JWT토큰_생성_테스트() {

        String accessToken = jwtProvider.createAccessToken(1L);
        String subject = jwtProvider.getSubject(accessToken);

        Assertions.assertThat(accessToken).isNotNull();
        Assertions.assertThat(String.valueOf(1L)).isEqualTo(subject);
    }


}
