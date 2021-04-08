package com.bywlstudio.common.exception;

/**
 * @Author: zl
 * @Date: Create in 2021/4/7 14:00
 * @Description:
 */

public class CourseException extends Exception{
    private String message ;

    public CourseException(String message) {
        super();
        this.message = message;
    }

}
