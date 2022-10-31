package com.ada.earthvalley.yomojomo.common.exceptions;

import com.ada.earthvalley.yomojomo.common.exceptions.errors.YomojomoTestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionTestController {
    @Autowired
    public ErrorInfo errorInfo;

    @GetMapping("/throws")
    void throwException() {
        throw YomojomoTestException.of(errorInfo);
    }
}
