package com.bywlstudio.common.entity;

import lombok.Data;

/**
 * @Author: zl
 * @Date: Create in 2021/4/7 13:34
 * @Description:
 */
public enum ResultCode {
    SUCCESS(20000,"SUCCESS"),ERROR(200001,"FAILURE");

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
