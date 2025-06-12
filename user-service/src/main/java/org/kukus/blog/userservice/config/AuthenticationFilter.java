package org.kukus.blog.userservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Извлекаем заголовки
        String username = request.getHeader("X-User-Name");
        String rolesHeader = request.getHeader("X-User-Roles");

        // Логирование для отладки
        System.out.println("Received X-User-Name: " + username);
        System.out.println("Received X-User-Roles: " + rolesHeader);

        // Проверяем, что заголовки присутствуют
        if (username == null || rolesHeader == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // Разбиваем строку с ролями на список
        List<String> roles = Arrays.asList(rolesHeader.split(","));

        // Сохраняем данные в атрибуты запроса для использования в контроллерах
        request.setAttribute("userName", username);
        request.setAttribute("roles", roles);

        System.out.println("Authentication successful, passing to filter chain");
        // Продолжаем обработку запроса
        filterChain.doFilter(request, response);
    }
}
