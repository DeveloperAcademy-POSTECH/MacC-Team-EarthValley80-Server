package com.ada.earthvalley.yomojomo.user.exceptions;

import static com.ada.earthvalley.yomojomo.common.exceptions.ErrorCode.*;

import org.springframework.http.HttpStatus;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorCode;
import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserError implements ErrorInfo {
	USER_NOT_FOUND(ERR1001)
	;

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
