package org.kukus.blog.gatewayservice.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Убираем "Bearer "
            try {
                Claims claims = jwtUtil.validateToken(token);
                // Создаем новый объект запроса с добавленными заголовками
                HttpServletRequest modifiedRequest = new HttpServletRequestWrapper(request) {
                    @Override
                    public String getHeader(String name) {
                        if ("X-User-Name".equalsIgnoreCase(name)) {
                            return claims.getSubject();
                        } else if ("X-User-Roles".equalsIgnoreCase(name)) {
                            return String.join(",", (List<String>) claims.get("roles"));
                        }
                        return super.getHeader(name);
                    }
                };
                filterChain.doFilter(modifiedRequest, response);
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        } else if (!isPublicPath(request.getRequestURI())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/auth/") || path.startsWith("/feed/");
    }
}
