package com.bywlstudio.common.exception.handler;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: zl
 * @Date: Create in 2021/4/7 13:42
 * @Description: 全局错误处理器
 */
@Component
public class GlobalErrorHandler extends DefaultErrorAttributes {

    /**
     *  重写错误处理机制
     * @param webRequest
     * @param includeStackTrace
     * @return
     */
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        return super.getErrorAttributes(webRequest, includeStackTrace);
    }
}
