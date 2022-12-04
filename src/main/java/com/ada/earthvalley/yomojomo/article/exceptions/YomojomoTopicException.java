package com.ada.earthvalley.yomojomo.article.exceptions;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;
import com.ada.earthvalley.yomojomo.common.exceptions.YomojomoException;

public class YomojomoTopicException extends YomojomoException {
	public YomojomoTopicException(ErrorInfo info) {
		super(info);
	}
}
