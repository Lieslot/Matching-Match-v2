package com.matchingMatch.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jwt-security.yml")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class JwtProvider {

    @Value("${spring.application.name}")
    private String iss;

    private SecretKey accessTokenPrivateKey;

    @Value("${jwt.expiredMill}")
    private Long expiredMill;

    @Autowired
    public JwtProvider(@Value("${jwt.accessToken.secretKeys}") String secretKeys) {
        this.accessTokenPrivateKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeys));
    }


    public String createAccessToken(Long id) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expiredMill);

        String token = Jwts.builder()
                           .issuer(iss)
                           .subject(String.valueOf(id))
                           .expiration(expiredDate)
                           .signWith(accessTokenPrivateKey)
                           .issuedAt(now)
                           .compact();

        log.info("token : {}", parseAccessToken(token));

        return token;
    }

    public String getSubject(final String token) {
        return parseAccessToken(token)
                .getPayload()
                .getSubject();
    }

    private Jws<Claims> parseAccessToken(final String token) {
        return Jwts.parser()
                   .decryptWith(accessTokenPrivateKey)
                   .build()
                   .parseSignedClaims(token);
    }


    public void validateToken(final String accessToken) {

            validateAccessToken(accessToken);

    }

    private void validateAccessToken(final String accessToken) {
        try {
            parseAccessToken(accessToken);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new IllegalArgumentException("토큰의 만료기간이 지났습니다.");
        }


    }


}
