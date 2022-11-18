package com.ada.earthvalley.yomojomo.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ada.earthvalley.yomojomo.auth.exceptions.YomojomoAuthException;
import com.ada.earthvalley.yomojomo.auth.jwt.BearerAuthenticationConverter;
import com.ada.earthvalley.yomojomo.auth.jwt.JwtAuthenticationToken;
import com.ada.earthvalley.yomojomo.auth.jwt.JwtUtilsService;
import com.ada.earthvalley.yomojomo.auth.jwt.dtos.YomojomoClaim;
import com.ada.earthvalley.yomojomo.common.exceptions.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final BearerAuthenticationConverter converter;
	private final JwtUtilsService jwtUtilsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			JwtAuthenticationToken authentication = converter.convert(request);
			YomojomoClaim claim = jwtUtilsService.verify(authentication.getCredentials());
			// TODO: setClaim 시 claim을 등록하면서 authentication = true 로 변경함. 인증 로직 추가 시 확인 요망 (by Leo - 22.11.18)
			authentication.setClaims(claim);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		} catch (YomojomoAuthException e) {
			// TODO: AuthenticationEntryPoint 를 이용해 refactoring (by Leo - 22.11.18)
			sendErrorResponse(response, e);
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return false;
	}

	private void sendErrorResponse(HttpServletResponse response, YomojomoAuthException e) throws
		IOException {
		response.setStatus(e.getInfo().getStatus().value());
		response.setContentType("application/json;charset=UTF-8");
		ErrorResponse errorResponse = new ErrorResponse(e.getInfo());
		ObjectMapper objectMapper = new ObjectMapper();
		String serializedString = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(errorResponse);
		response.getWriter().print(serializedString);
	}
}
