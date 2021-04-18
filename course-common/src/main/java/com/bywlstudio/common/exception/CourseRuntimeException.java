package com.bywlstudio.common.exception;

/**
 * @Author: zl
 * @Date: Create in 2021/4/16 21:58
 * @Description:
 */
public class CourseRuntimeException extends RuntimeException {
    private String message ;
    private int code ;

    public CourseRuntimeException(String message,int code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
