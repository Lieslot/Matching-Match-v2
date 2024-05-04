package com.matchingMatch.auth.resolver;

import com.matchingMatch.auth.Authentication;
import com.matchingMatch.auth.JwtProvider;
import com.matchingMatch.auth.dto.UserAuth;
import com.matchingMatch.match.domain.Team;
import com.matchingMatch.match.domain.repository.TeamRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.Optional;
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

    private final TeamRepository teamRepository;

    private final JwtProvider jwtProvider;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(Authentication.class);

    }

    @Override
    public UserAuth resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String jwtToken = webRequest.getHeader("Authorization");
        log.info("raw jwt : {}", jwtToken);

        Long subject = Long.parseLong( jwtProvider.getSubject(jwtToken));

        return UserAuth.team(subject);
    }



}
