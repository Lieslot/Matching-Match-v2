package com.matchingMatch.auth.resolver;

import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.JwtProvider;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.domain.repository.TeamRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@RequiredArgsConstructor
@Component
@Slf4j
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String TEAM_ID = "TEAM_ID";

    private static final String REFRESH_TOKEN_KEY = "refresh_token";

    private static final String ACCESS_TOKEN_KEY = "Authorization";

    private final JwtProvider jwtProvider;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(Authentication.class);

    }


    @Override
    public UserAuth resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String accessToken = webRequest.getHeader(ACCESS_TOKEN_KEY);
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        if (request == null) {

            throw new IllegalArgumentException();
        }

        try {
            String refreshToken = parseRefreshToken(request);

            jwtProvider.validateToken(accessToken, refreshToken);
            log.info("raw jwt : {}", accessToken);

            Long subject = Long.parseLong(jwtProvider.getSubject(accessToken));

            return UserAuth.team(subject);

        } catch (RuntimeException e) {
            return UserAuth.guest();

        }

    }

    private String parseRefreshToken(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName()
                        .equals(REFRESH_TOKEN_KEY))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .getValue();
    }


}
