package com.ada.earthvalley.yomojomo.auth.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.ada.earthvalley.yomojomo.auth.exceptions.AuthError;
import com.ada.earthvalley.yomojomo.common.exceptions.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class OAuth2AuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		setHttpStatus(response);
		setResponseHeader(response);
		setResponsePayload(response);
	}

	private void setResponseHeader(HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
	}

	private void setHttpStatus(HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_FORBIDDEN);
	}

	private void setResponsePayload(HttpServletResponse response) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ErrorResponse errorResponse = ErrorResponse.createResponseEntity(
			AuthError.OAUTH2_AUTHENTICATION_REQUIRED).getBody();
		String serializedString = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(errorResponse);
		response.getWriter().print(serializedString);
	}
}
