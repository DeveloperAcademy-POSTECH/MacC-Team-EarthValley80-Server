package com.ada.earthvalley.yomojomo.common.exceptions;

import org.springframework.http.HttpStatus;

public interface ErrorInfo {
    String getMessage();
    int getCode();

    HttpStatus getStatus();
}
