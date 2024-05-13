package com.matchingMatch.auth;


import com.matchingMatch.auth.service.AuthService;
import com.matchingMatch.match.domain.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AuthServiceTest {


    @Autowired
    private AuthService authService;

    // TODO 시나리오별(토큰 저장소에 토큰이 있는 경우 없는 경우) 테스트



}
