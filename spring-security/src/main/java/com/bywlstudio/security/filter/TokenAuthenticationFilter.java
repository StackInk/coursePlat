package com.bywlstudio.security.filter;

import com.bywlstudio.common.util.JwtUtil;
import com.bywlstudio.common.util.R;
import com.bywlstudio.common.util.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zl
 * @Date: Create in 2021/4/9 16:50
 * @Description:
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private RedisTemplate<String,Object> redisTemplate;



    public TokenAuthenticationFilter(AuthenticationManager authenticationManager,RedisTemplate<String,Object> redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
    }


    /**
     * 处理所有的请求，属于一个过滤器链
     *  判断当前请求是否需要进行登陆操作，如果需要则，将用户信息封装为一个UsernamePasswordToken对象
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.info("开始处理请求");
        if (!request.getRequestURI().contains("admin")) {
            chain.doFilter(request,response);
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);

        if(Objects.isNull(authenticationToken)) {
            ResponseUtil.out(response,R.error());
            return ;
        }
        //设置全局认证对象
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request,response);
    }


    /**
     *  从请求中获取 token 信息。封装一个UsernamePasswordAuthenticationToken 对象
     * @param request
     * @return 封装好的用户对象，包括用户的权限信息，从 Redis 中获取对应的权限信息
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            String username = JwtUtil.getMemberIdByJwtToken(token);
            if (StringUtils.isEmpty(username)) return null;
            List<String> permissionValueList = (List<String>)redisTemplate.opsForValue().get(username);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            permissionValueList.forEach(permissionValue->{
                if(!StringUtils.isEmpty(permissionValue)) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority((permissionValue));
                    authorities.add(authority);
                }
            });
            return new UsernamePasswordAuthenticationToken(username,token,authorities);
        }
        return null;
    }


}
