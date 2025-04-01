package com.gong.lease.common.exception;

import com.gong.lease.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public Result handle(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }


    @ExceptionHandler(LeaseException.class)
    public Result handle(LeaseException e) {
        String message = e.getMessage();
        Integer code = e.getCode();
        e.printStackTrace();
        return Result.fail(code, message);
    }
}
