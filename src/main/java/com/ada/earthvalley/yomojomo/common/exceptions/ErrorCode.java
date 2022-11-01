package com.ada.earthvalley.yomojomo.common.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	;
	private final int code;
	private final HttpStatus status;
	private final String message;
}
