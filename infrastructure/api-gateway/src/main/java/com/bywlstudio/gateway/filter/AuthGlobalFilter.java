package com.bywlstudio.gateway.filter;

import com.bywlstudio.common.util.JwtUtil;
import com.bywlstudio.common.util.R;
import com.bywlstudio.common.util.ResponseUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * @Author: zl
 * @Date: Create in 2021/4/9 15:53
 * @Description:
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        //对用户操作进行鉴权，判断是否登陆
        if(antPathMatcher.match("/**/auth/**", path)) {
            List<String> tokenList = request.getHeaders().get("token");
            if(Objects.isNull(tokenList)) {
                ServerHttpResponse response = exchange.getResponse();
                return ResponseUtil.fluxOut(response,R.error().data("message","用户未登陆"));
            } else {
                Boolean isCheck = JwtUtil.checkToken(tokenList.get(0));
                if(!isCheck) {
                    ServerHttpResponse response = exchange.getResponse();
                    return ResponseUtil.fluxOut(response,R.error().data("message","token过期"));
                }
            }
        }
        //内部服务接口，不允许外部访问
        if(antPathMatcher.match("/**/inner/**", path)) {
            ServerHttpResponse response = exchange.getResponse();
            return ResponseUtil.fluxOut(response, R.error().data("message","接口不允许访问"));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
