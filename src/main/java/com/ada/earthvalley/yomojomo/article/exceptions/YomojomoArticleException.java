package com.ada.earthvalley.yomojomo.article.exceptions;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;
import com.ada.earthvalley.yomojomo.common.exceptions.YomojomoException;

public class YomojomoArticleException extends YomojomoException {
	public YomojomoArticleException(ErrorInfo info) {
		super(info);
	}
}
