package com.ada.earthvalley.yomojomo.common.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	ERR1000(1000, HttpStatus.NOT_FOUND, "NIE가 존재하지 않습니다.");

	private final int code;
	private final HttpStatus status;
	private final String message;
}
