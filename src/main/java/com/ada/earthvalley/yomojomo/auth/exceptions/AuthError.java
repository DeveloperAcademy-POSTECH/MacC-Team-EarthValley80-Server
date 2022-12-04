package com.ada.earthvalley.yomojomo.auth.exceptions;

import static com.ada.earthvalley.yomojomo.common.exceptions.ErrorCode.*;

import org.springframework.http.HttpStatus;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorCode;
import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthError implements ErrorInfo {
	ILLEGAL_AUTH_HEADER(ERR2000),
	INVALID_JWT(ERR2001),
	JWT_EXPIRED(ERR2002),
	NOT_A_MEMBER(ERR2003),
	OAUTH2_AUTHENTICATION_REQUIRED(ERR2004),
	ALREADY_A_MEMBER(ERR2005);

	private final ErrorCode code;

	@Override
	public String getMessage() {
		return code.getMessage();
	}

	@Override
	public int getCode() {
		return code.getCode();
	}

	@Override
	public HttpStatus getStatus() {
		return code.getStatus();
	}
}
