package com.ada.earthvalley.yomojomo.auth.oauth2;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ada.earthvalley.yomojomo.auth.YomojomoOAuth2User;

public class OAuth2VendorForwardFilter extends OncePerRequestFilter {
	public static final String LOGIN_URI = "/api/oauth2/login";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		YomojomoOAuth2User user = (YomojomoOAuth2User) authentication.getPrincipal();
		String forwardURI = LOGIN_URI;
		switch (user.getVendorType()) {
			case KAKAO:
				forwardURI += "/kakao";
			default:
				request.getRequestDispatcher(forwardURI).forward(request, response);
		}

		doFilter(request, response, filterChain);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getServletPath().matches(LOGIN_URI);
	}
}
