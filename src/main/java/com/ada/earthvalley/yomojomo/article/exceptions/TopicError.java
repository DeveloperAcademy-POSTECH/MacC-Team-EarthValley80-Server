package com.ada.earthvalley.yomojomo.article.exceptions;

import org.springframework.http.HttpStatus;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorCode;
import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TopicError implements ErrorInfo {
	TOPIC_NOT_FOUND(ErrorCode.ERR3000);

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
