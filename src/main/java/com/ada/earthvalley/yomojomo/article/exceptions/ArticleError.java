package com.ada.earthvalley.yomojomo.article.exceptions;

import static com.ada.earthvalley.yomojomo.common.exceptions.ErrorCode.*;

import org.springframework.http.HttpStatus;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorCode;
import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ArticleError implements ErrorInfo {
	ARTICLE_NOT_FOUND(ERR4000),
	WEEKLY_ARTICLE_NOT_FOUND(ERR4001);
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
