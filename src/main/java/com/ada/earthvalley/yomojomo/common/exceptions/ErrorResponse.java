package com.ada.earthvalley.yomojomo.common.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private final LocalDateTime timestamp = LocalDateTime.now();
	private final String status;
	private final int code;
	private final String message;
	// TODO: BindException 을 위한 FieldError 정의 (by Leo 22.10.31)

	public static ResponseEntity<ErrorResponse> createResponseEntity(ErrorInfo info) {
		return ResponseEntity.status(info.getStatus()).body(new ErrorResponse(info));
	}

	private ErrorResponse(ErrorInfo info) {
		this.status = stateToString(info.getStatus());
		this.code = info.getCode();
		this.message = info.getMessage();
	}

	private String stateToString(HttpStatus status) {
		return status.value() + " " + status.getReasonPhrase().toUpperCase();
	}
}