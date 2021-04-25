package com.bywlstudio.security.security;

import com.bywlstudio.common.util.JwtUtil;
import com.bywlstudio.common.util.R;
import com.bywlstudio.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zl
 * @Date: Create in 2021/4/9 16:30
 * @Description:
 */
@Component
@Slf4j
public class TokenLogoutHandler implements LogoutHandler {


    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 从 Redis 中移除用户信息，将Token移除(服务端无法处理，客户端移除token)
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     */
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String userId = JwtUtil.getMemberIdByJwtToken(httpServletRequest.getHeader("token"));
        redisTemplate.unlink(userId);
        log.info("{}用户注销成功",userId);
        ResponseUtil.out(httpServletResponse, R.ok().data("message","注销成功"));
    }
}
