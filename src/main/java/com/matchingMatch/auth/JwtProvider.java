package com.matchingMatch.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@PropertySource("classpath:secret.yml")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class JwtProvider {

	@Value("${spring.application.name}")
	private String iss;

	private SecretKey accessTokenPrivateKey;

	private SecretKey refreshTokenPrivateKey;

	@Value("${jwt.accessTokenExpiredMill}")
	private Long accessTokenExpiredMill;

	@Value("${jwt.refreshTokenExpiredMill}")
	private Long refreshTokenExpiredMill;

	@Autowired
	public JwtProvider(
		@Value("${jwt.accessToken.secretKeys}") String accessTokenSecretKey,
		@Value("${jwt.refreshToken.secretKey}") String refreshTokenSecretKey) {
		this.accessTokenPrivateKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(accessTokenSecretKey));
		this.refreshTokenPrivateKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(refreshTokenSecretKey));
	}

	public String createAccessToken(Long id) {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + accessTokenExpiredMill);

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

	public String createRefreshToken(Long id) {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + refreshTokenExpiredMill);

		String token = Jwts.builder()
			.issuer(iss)
			.subject(String.valueOf(id))
			.expiration(expiredDate)
			.signWith(refreshTokenPrivateKey)
			.issuedAt(now)
			.compact();

		log.info("token : {}", parseRefreshToken(token));

		return token;
	}

	public String getSubject(final String token) {
		return parseAccessToken(token)
			.getPayload()
			.getSubject();
	}

	private Jws<Claims> parseAccessToken(final String token) {
		return Jwts.parser()
			.verifyWith(accessTokenPrivateKey)
			.build()
			.parseSignedClaims(token);
	}

	private Jws<Claims> parseRefreshToken(final String token) {
		return Jwts.parser()
			.verifyWith(refreshTokenPrivateKey)
			.build()
			.parseSignedClaims(token);
	}

	public void validateToken(final String accessToken, final String refreshToken) {

		validateAccessToken(accessToken);
		validateRefreshToken(refreshToken);

	}

	private void validateAccessToken(final String accessToken) {
		try {
			parseAccessToken(accessToken);
		} catch (ExpiredJwtException expiredJwtException) {
			throw new IllegalArgumentException("토큰의 만료기간이 지났습니다.");
		}

	}

	private void validateRefreshToken(final String refreshToken) {
		try {
			parseRefreshToken(refreshToken);
		} catch (ExpiredJwtException expiredJwtException) {
			throw new IllegalArgumentException("토큰의 만료기간이 지났습니다.");
		}

	}

}
