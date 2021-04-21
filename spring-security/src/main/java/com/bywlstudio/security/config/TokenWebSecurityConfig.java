package com.bywlstudio.security.config;

import com.bywlstudio.common.constant.CMedia;
import com.bywlstudio.common.util.R;
import com.bywlstudio.common.util.ResponseUtil;
import com.bywlstudio.security.filter.TokenAuthenticationFilter;
import com.bywlstudio.security.filter.TokenLoginFilter;
import com.bywlstudio.security.security.TokenLogoutHandler;
import com.bywlstudio.security.security.UnauthorizedEntryPoint;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zl
 * @Date: Create in 2021/4/9 16:13
 * @Description:  基础配置类，对于 WebSecurityConfigurerAdapter 来说，他提供对所有的请求的拦截策略，通过重写configure方法来实现
 * 自己的策略.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private RedisTemplate<String,Object> redisTemplate ;
    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 基础配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and()
                    .csrf().disable()
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .logout().logoutUrl("/acl/admin/index/logout")
                    .addLogoutHandler(new TokenLogoutHandler())
                .and()
                    .addFilter(new TokenAuthenticationFilter(authenticationManager(),redisTemplate))
                    .addFilter(new TokenLoginFilter(authenticationManager(),redisTemplate));

    }

    /**
     * 设置UserDetailService 和密码加密
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-resources/**","/v2/**","swagger-ui.html/**");
    }

}
