package com.ada.earthvalley.yomojomo.auth.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


// TODO: 싱글톤으로 리팩터링 (by Leo - 22.10.30)
// TODO: Exception 수정 (by Leo - 22.10.30)
@Component
public class BearerAuthenticationConverter implements AuthenticationConverter {
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public JwtAuthenticationToken convert(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            return null;
        }
        header = header.trim();
        String[] splited = header.split(" ");
        if (splited.length != 2) {
            throw new IllegalArgumentException("올바르지 않은 형식");
        }
        final String scheme = splited[0];
        final String token = splited[1];
        if (!AUTHENTICATION_SCHEME.equals(scheme)) {
            throw new IllegalArgumentException("Bearer 가 아님");
        }
        return JwtAuthenticationToken.unauthenticated(token);
    }
}
