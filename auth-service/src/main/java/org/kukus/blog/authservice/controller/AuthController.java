package org.kukus.blog.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kukus.blog.authservice.dto.LoginRequest;
import org.kukus.blog.authservice.dto.RegisterRequest;
import org.kukus.blog.authservice.model.User;
import org.kukus.blog.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("token", authService.login(request));
        return ResponseEntity.ok(response);
    }
}

//TODO
//Токен недействителен (например, истёк срок действия или подпись не совпадает).
//gateway-service не может проверить токен из-за несоответствия ключа подписи.
//gateway-service перенаправляет запрос в user-service, но токен теряется или не передаётся.
