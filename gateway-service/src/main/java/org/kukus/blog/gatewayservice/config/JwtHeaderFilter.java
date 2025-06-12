package org.kukus.blog.gatewayservice.config;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtHeaderFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    public JwtHeaderFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
                try {
                    Claims claims = jwtUtil.getClaimsFromToken(token);
                    String username = claims.getSubject();
                    String roles = String.join(",", jwtUtil.getRolesFromToken(token));
                    System.out.println(username + " " + roles);
                    // Добавляем заголовки
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header("X-User-Name", username)
                            .header("X-User-Roles", roles)
                            .build();

                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                } catch (Exception e) {
                    // если токен невалиден — можно прервать или пропустить дальше
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
        }
        return chain.filter(exchange);
    }
}
