package com.ada.earthvalley.yomojomo.user.exceptions;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;
import com.ada.earthvalley.yomojomo.common.exceptions.YomojomoException;

public class YomojomoUserException extends YomojomoException {
	public YomojomoUserException(ErrorInfo info) {
		super(info);
	}
}
