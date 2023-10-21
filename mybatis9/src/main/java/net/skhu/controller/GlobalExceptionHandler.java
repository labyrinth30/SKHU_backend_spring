package net.skhu.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception) {
        log.error("전역 에러", exception); // 에러를 로그에 그록
        return "error";                     // 뷰(error.html)의 이름을 리턴
    }

}

