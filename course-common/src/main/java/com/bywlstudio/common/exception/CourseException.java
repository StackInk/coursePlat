package com.bywlstudio.common.exception;

/**
 * @Author: zl
 * @Date: Create in 2021/4/7 14:00
 * @Description:
 */

public class CourseException extends Exception{
    private String message ;
    private int code ;

    public CourseException(String message,int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

}
