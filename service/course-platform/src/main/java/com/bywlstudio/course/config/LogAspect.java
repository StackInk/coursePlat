package com.bywlstudio.course.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author: zl
 * @Date: Create in 2021/5/9 22:23
 * @Description:
 */
@Aspect
@Component
@Order(2)
@Slf4j
public class LogAspect {

    @Pointcut("execution(public * com.bywlstudio.course.controller..*.*(..))")
    public void controllerLog(){}

    @Before("controllerLog()")
    public void logBeforeController(JoinPoint joinPoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

        log.info("################URL : " + request.getRequestURL().toString());
        log.info("################HTTP_METHOD : " + request.getMethod());
        log.info("################IP : " + request.getRemoteAddr());
        log.info("################THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));
        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        log.info("################CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }


}
