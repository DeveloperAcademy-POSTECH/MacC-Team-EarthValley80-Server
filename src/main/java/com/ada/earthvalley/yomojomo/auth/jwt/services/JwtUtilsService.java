package com.ada.earthvalley.yomojomo.auth.jwt.services;

import static com.ada.earthvalley.yomojomo.auth.exceptions.AuthError.*;

import org.springframework.stereotype.Service;

import com.ada.earthvalley.yomojomo.auth.exceptions.YomojomoAuthException;
import com.ada.earthvalley.yomojomo.auth.jwt.dtos.YomojomoClaim;
import com.ada.earthvalley.yomojomo.auth.jwt.enums.TokenType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtUtilsService {
	private final JwtSecretsService jwtSecretsService;

	public YomojomoClaim verify(String jwsString) throws YomojomoAuthException {
		return verify(parseJws(jwsString));
	}

	public String createAccessToken(YomojomoClaim claim) throws InvalidKeyException {
		return create(claim, TokenType.ACCESS_TOKEN);
	}

	public String createRefreshToken(YomojomoClaim claim) throws InvalidKeyException {
		return create(claim, TokenType.REFRESH_TOKEN);
	}

	private String create(YomojomoClaim claim, TokenType type) throws InvalidKeyException {
		return Jwts.builder()
			.setSubject(claim.getUserId())
			.setIssuer(jwtSecretsService.getIssuer())
			.setIssuedAt(jwtSecretsService.getIssuedAt())
			.setExpiration(jwtSecretsService.getExpiredAt(type))
			.signWith(jwtSecretsService.getSecretKey())
			.compact();
	}

	private Jws<Claims> parseJws(String jwsString) throws YomojomoAuthException {
		try {
			return Jwts.parserBuilder()
				.requireIssuer(jwtSecretsService.getIssuer())
				.setSigningKey(jwtSecretsService.getSecretKey())
				.build()
				.parseClaimsJws(jwsString);
		} catch (InvalidClaimException | SignatureException | UnsupportedJwtException | MalformedJwtException e) {
			throw new YomojomoAuthException(INVALID_JWT);
		} catch (ExpiredJwtException e) {
			throw new YomojomoAuthException(JWT_EXPIRED);
		}
	}

	// Claim 검증 후 throw
	private YomojomoClaim verify(Jws<Claims> claims) throws YomojomoAuthException {
		return YomojomoClaim.of(claims.getBody());
	}
}
