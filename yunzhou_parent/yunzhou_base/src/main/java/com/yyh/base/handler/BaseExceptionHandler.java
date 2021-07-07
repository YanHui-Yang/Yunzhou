package com.yyh.base.handler;

import com.yyh.entity.Result;
import com.yyh.entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Author: Tong
 * Date: 2021/4/1
 * Description: 统一异常处理类
 */
@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result error(Exception e) {
        return new Result(StatusCode.ERROR, e.getMessage());
    }
}
