package com.ada.earthvalley.yomojomo.common.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.earthvalley.yomojomo.common.exceptions.errors.YomojomoTestException;

@RestController
public class ExceptionTestController {
    @Autowired
    public ErrorInfo errorInfo;

    @GetMapping("/throws")
    void throwException() {
        throw YomojomoTestException.of(errorInfo);
    }
}
