package com.ada.earthvalley.yomojomo.common.exceptions;

import lombok.Getter;

@Getter
public class YomojomoException extends RuntimeException {
    private ErrorInfo info;

    private YomojomoException() {
    }

    protected YomojomoException(ErrorInfo info) {
        super(info.getMessage());
        this.info = info;
    }

    public static YomojomoException of(ErrorInfo code) {
        return new YomojomoException(code);
    }
}
