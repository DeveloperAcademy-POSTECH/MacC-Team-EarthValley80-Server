package com.ada.earthvalley.yomojomo.common.exceptions.errors;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;
import com.ada.earthvalley.yomojomo.common.exceptions.YomojomoException;

public class YomojomoTestException extends YomojomoException {
	private YomojomoTestException(ErrorInfo info) {
		super(info);
	}
}
