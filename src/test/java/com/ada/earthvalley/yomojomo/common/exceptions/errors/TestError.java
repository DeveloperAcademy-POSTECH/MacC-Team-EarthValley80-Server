package com.ada.earthvalley.yomojomo.common.exceptions.errors;

import com.ada.earthvalley.yomojomo.common.exceptions.ErrorCode;
import com.ada.earthvalley.yomojomo.common.exceptions.ErrorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum TestError implements ErrorInfo {
    ;

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
