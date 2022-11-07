package com.ada.earthvalley.yomojomo.common.exceptions.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorResponse;
import com.ada.earthvalley.yomojomo.common.exceptions.YomojomoException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(YomojomoException.class)
	protected ResponseEntity<ErrorResponse> handleYomojomoException(YomojomoException e) {
		log.error("Yomojomo Custom Exception: {}", e.getMessage());
		return ErrorResponse.createResponseEntity(e.getInfo());
	}
}
