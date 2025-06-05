package org.kukus.blog.gatewayservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Разрешаем CORS для всех эндпоинтов
//                .allowedOrigins(
//                        "http://localhost:5174",
//                        "http://192.168.3.152:5174",
//                        "http://192.168.193.6:5174",
//                        "http://192.168.3.152:5174" // Добавил повторно для проверки
//                )
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*") // Разрешаем все заголовки
//                .allowCredentials(true) // Разрешаем куки и авторизацию
//                .maxAge(3600); // Кэшируем настройки CORS на 1 час
//    }
}
