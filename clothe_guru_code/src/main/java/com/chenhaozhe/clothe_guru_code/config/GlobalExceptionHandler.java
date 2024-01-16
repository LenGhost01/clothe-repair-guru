package com.chenhaozhe.clothe_guru_code.config;

import com.chenhaozhe.clothe_guru_code.exception.CustomInputMismatchException;
import com.chenhaozhe.clothe_guru_code.exception.DatabaseNotChangeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

// 全局异常处理机，用于捕获特定异常，向前端返回错误信息
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 使用校验注解时报错返回错误信息
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> exceptionHandler(MethodArgumentNotValidException ex){
        List<String> errorMessage = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errorMessage.add(error.getDefaultMessage()); // 这里获取的是注解中设置的 message
        });
        return errorMessage;
    }

    // 个人自定义异常处理
    @ExceptionHandler(CustomInputMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> exceptionHandler(CustomInputMismatchException cex){
        List<String> errorMessage = new ArrayList<>();
        errorMessage.add(cex.getMessage());
        return errorMessage;
    }

    @ExceptionHandler(DatabaseNotChangeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public List<String> exceptionHandler(DatabaseNotChangeException cex){
        List<String> errorMessage = new ArrayList<>();
        errorMessage.add(cex.getMessage());
        return errorMessage;
    }
}
