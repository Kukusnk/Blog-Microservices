package org.kukus.blog.gatewayservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешаем CORS для всех эндпоинтов
                .allowedOrigins("http://localhost:5174", "http://26.211.32.52:5174",
                        "http://192.168.193.6:5174", "http://192.168.3.152:5174") // Разрешаем запросы с этого источника
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешенные методы
                .allowedHeaders("*") // Разрешенные заголовки
                .allowCredentials(true); // Разрешить передачу куки и авторизации
    }
}
