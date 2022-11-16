package com.ada.earthvalley.yomojomo.common.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	ERR1000(1000, HttpStatus.NOT_FOUND, "NIE가 존재하지 않습니다."),

	ERR2000(2000, HttpStatus.BAD_REQUEST, "인증 헤더가 형식에 맞지 않습니다."),
	ERR2001(2001, HttpStatus.UNAUTHORIZED, "올바르지 않은 인증 토큰입니다."),
	ERR2002(2002, HttpStatus.UNAUTHORIZED, "인증 토큰이 만료되었습니다.");

	private final int code;
	private final HttpStatus status;
	private final String message;
}
