package com.ada.earthvalley.yomojomo.common.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// TODO: NIE 부분 삭제
	// 1000 - User
	ERR1000(1000, HttpStatus.NOT_FOUND, "NIE가 존재하지 않습니다."),
	ERR1001(1001, HttpStatus.NOT_FOUND, "존재하지 않는 유저 입니다."),

	// 2000 - Auth
	ERR2000(2000, HttpStatus.BAD_REQUEST, "인증 헤더가 형식에 맞지 않습니다."),
	ERR2001(2001, HttpStatus.UNAUTHORIZED, "올바르지 않은 인증 토큰입니다."),
	ERR2002(2002, HttpStatus.UNAUTHORIZED, "인증 토큰이 만료되었습니다."),
	ERR2003(2003, HttpStatus.FORBIDDEN, "존재하지 않는 회원입니다. \n먼저 회원가입을 진행해주세요."),
	ERR2004(2004, HttpStatus.FORBIDDEN, "OAuth2 인증이 필요한 리소스 입니다."),
	ERR2005(2005, HttpStatus.BAD_REQUEST, "이미 회원인 유저입니다."),

	// 3000 - Topic
	ERR3000(3000, HttpStatus.NOT_FOUND, "존재하지 않는 토픽입니다."),

	// 4000 - Article
	ERR4000(4000, HttpStatus.NOT_FOUND, "존재하지 않는 글입니다."),

	ERR4001(4001, HttpStatus.NOT_FOUND, "이번주의 글이 존재하지 않습니다. ");

	private final int code;
	private final HttpStatus status;
	private final String message;
}
