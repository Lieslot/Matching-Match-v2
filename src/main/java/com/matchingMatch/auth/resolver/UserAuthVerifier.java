package com.matchingMatch.auth.resolver;


import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.domain.enums.Role;
import java.util.stream.Stream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAuthVerifier {
    private static final String INVALID_AUTHORITY = "권한이 없습니다.";

    // TODO ADMIN인지 체크하기


    @Before("@annotation(com.matchingMatch.auth.AuthenticatedUser)")
    public void verifyUser(JoinPoint joinPoint) {
        Stream.of(joinPoint.getArgs())
              .filter(UserAuth.class::isInstance) // Object 클래스에 대해 UserAuth로 캐스트 가능한지 체크
              .map(UserAuth.class::cast) // UserAuth 클래스로 캐스트
              .filter(userAuth -> userAuth.getRole() == Role.USER)
              .findFirst()
              .orElseThrow(() -> new IllegalArgumentException(INVALID_AUTHORITY));

    }


}
