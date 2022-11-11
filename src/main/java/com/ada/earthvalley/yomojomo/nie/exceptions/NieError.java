package com.ada.earthvalley.yomojomo.nie.exceptions;

import static com.ada.earthvalley.yomojomo.common.exceptions.ErrorCode.*;

import org.springframework.http.HttpStatus;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorCode;
import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum NieError implements ErrorInfo {
	NIE_NOT_FOUND(ERR1000);

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
