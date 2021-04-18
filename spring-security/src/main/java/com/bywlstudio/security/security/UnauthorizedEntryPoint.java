package com.bywlstudio.security.security;

import com.bywlstudio.common.util.R;
import com.bywlstudio.common.util.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zl
 * @Date: Create in 2021/4/9 16:47
 * @Description: 当过滤器中出现异常的时候，直接执行当前逻辑 ExceptionTranslationFilter 处理
 */
@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(httpServletResponse, R.error().data("message","鉴权失败"));
    }
}
