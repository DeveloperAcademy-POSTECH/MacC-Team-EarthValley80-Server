package com.ada.earthvalley.yomojomo.common.exceptions.errors;

import org.springframework.http.HttpStatus;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorCode;
import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TestError implements ErrorInfo {
	;

	private ErrorCode code;

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
