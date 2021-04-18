package com.bywlstudio.security.filter;

import com.bywlstudio.common.exception.CourseException;
import com.bywlstudio.common.exception.CourseRuntimeException;
import com.bywlstudio.common.util.JsonUtil;
import com.bywlstudio.common.util.JwtUtil;
import com.bywlstudio.common.util.R;
import com.bywlstudio.common.util.ResponseUtil;
import com.bywlstudio.security.entity.SecurityUser;
import com.bywlstudio.security.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author: zl
 * @Date: Create in 2021/4/16 21:41
 * @Description: 处理登陆请求，重写处理表单的逻辑
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;
    private RedisTemplate<String,Object> redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
    }


    /**
     * 验证的过程，获取用户名和密码
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user = null ;
        try{
            user = JsonUtil.getClass(request.getInputStream(), User.class);
        }catch (Exception e){
            logger.info("解析用户信息失败");
            throw new CourseRuntimeException(e.getMessage(),20005);
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),new ArrayList<>()));
    }


    /**
     * 验证成功的处理逻辑:1.获取token;2. Redis 中写入权限列表信息 3. 将Token 返回给客户端
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityUser user = (SecurityUser)authResult.getPrincipal();
        String token = JwtUtil.getJwtToken(user.getUser().getUsername());
        redisTemplate.opsForValue().set(user.getUser().getUsername(),user.getPermissionValueList());
        ResponseUtil.out(response, R.ok().data("token",token));
    }


    /**
     * 验证是失败的处理逻辑
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response,R.error());
    }
}
