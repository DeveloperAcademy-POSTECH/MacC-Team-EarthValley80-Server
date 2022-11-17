package com.ada.earthvalley.yomojomo.auth.jwt;

import static com.ada.earthvalley.yomojomo.auth.exceptions.AuthError.*;

import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import com.ada.earthvalley.yomojomo.auth.exceptions.YomojomoAuthException;

// TODO: 싱글톤으로 리팩터링 (by Leo - 22.10.30)
// TODO: Exception 수정 (by Leo - 22.10.30)
@Component
public class BearerAuthenticationConverter implements AuthenticationConverter {
	private static final String AUTHENTICATION_SCHEME = "Bearer";

	@Override
	public JwtAuthenticationToken convert(HttpServletRequest request) throws YomojomoAuthException {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header == null) {
			throw new YomojomoAuthException(ILLEGAL_AUTH_HEADER);
		}
		header = header.trim();
		String[] splited = header.split(" ");
		if (splited.length != 2) {
			throw new YomojomoAuthException(ILLEGAL_AUTH_HEADER);
		}
		final String scheme = splited[0];
		final String token = splited[1];
		if (!AUTHENTICATION_SCHEME.equals(scheme)) {
			throw new YomojomoAuthException(ILLEGAL_AUTH_HEADER);
		}
		return JwtAuthenticationToken.unauthenticated(token);
	}
}
