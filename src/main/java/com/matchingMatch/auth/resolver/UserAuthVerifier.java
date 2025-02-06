package com.matchingMatch.auth.resolver;

import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.domain.enums.Role;
import com.matchingMatch.match.exception.UnauthorizedAccessException;

@Aspect
@Component
public class UserAuthVerifier {

	// TODO ADMIN인지 체크하기

	@Before("@annotation(com.matchingMatch.auth.AuthenticatedAdmin)")
	public void verifyAdmin(final JoinPoint joinPoint) {
		Stream.of(joinPoint.getArgs())
			.filter(UserAuth.class::isInstance) // Object 클래스에 대해 UserAuth로 캐스트 가능한지 체크
			.map(UserAuth.class::cast) // UserAuth 클래스로 캐스트
			.filter(userAuth -> userAuth.getRole() == Role.ADMIN)
			.findFirst()
			.orElseThrow(UnauthorizedAccessException::new);

	}

	@Before("@annotation(com.matchingMatch.auth.AuthenticatedUser)")
	public void verifyUser(JoinPoint joinPoint) {

		Stream.of(joinPoint.getArgs())
			.filter(UserAuth.class::isInstance) // Object 클래스에 대해 UserAuth로 캐스트 가능한지 체크
			.map(UserAuth.class::cast) // UserAuth 클래스로 캐스트
			.filter(userAuth -> userAuth.getRole() == Role.USER)
			.findFirst()
			.orElseThrow(UnauthorizedAccessException::new);
	}

}
