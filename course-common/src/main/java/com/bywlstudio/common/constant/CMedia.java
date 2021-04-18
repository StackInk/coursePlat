package com.bywlstudio.common.constant;

/**
 * @Author: zl
 * @Date: Create in 2021/4/16 16:33
 * @Description:
 */
public enum CMedia {

    JSON("application/json;charset=UTF-8");

    private String message ;

    CMedia(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
