package com.ada.earthvalley.yomojomo.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ada.earthvalley.yomojomo.auth.jwt.BearerAuthenticationConverter;
import com.ada.earthvalley.yomojomo.auth.jwt.JwtAuthenticationToken;
import com.ada.earthvalley.yomojomo.auth.jwt.JwtUtilsService;
import com.ada.earthvalley.yomojomo.auth.jwt.dtos.YomojomoClaim;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final BearerAuthenticationConverter converter;
	private final JwtUtilsService jwtUtilsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		JwtAuthenticationToken authentication = converter.convert(request);
		YomojomoClaim claim = jwtUtilsService.verify(authentication.getCredentials());
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return false;
	}
}
