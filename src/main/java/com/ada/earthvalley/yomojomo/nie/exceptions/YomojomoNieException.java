package com.ada.earthvalley.yomojomo.nie.exceptions;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;
import com.ada.earthvalley.yomojomo.common.exceptions.YomojomoException;

public class YomojomoNieException extends YomojomoException {
	public YomojomoNieException(ErrorInfo info) {
		super(info);
	}
}
