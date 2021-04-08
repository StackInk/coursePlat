package com.bywlstudio.common.exception.handler;

import com.bywlstudio.common.exception.CourseException;
import com.bywlstudio.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @Author: zl
 * @Date: Create in 2021/4/7 13:41
 * @Description: 全局异常处理机制
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(CourseException.class)
    public R courseException(CourseException courseException) {
        return R.error().data("error",courseException.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public R methodArgumentNotValidException(MethodArgumentNotValidException e,ConstraintViolationException e1) {
        return R.error().data("error",e.getMessage());
    }


}
