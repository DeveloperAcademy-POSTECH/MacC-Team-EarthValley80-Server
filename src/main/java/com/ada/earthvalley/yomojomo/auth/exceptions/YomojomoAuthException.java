package com.ada.earthvalley.yomojomo.auth.exceptions;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;
import com.ada.earthvalley.yomojomo.common.exceptions.YomojomoException;

public class YomojomoAuthException extends YomojomoException {
	public YomojomoAuthException(ErrorInfo info) {
		super(info);
	}
}
