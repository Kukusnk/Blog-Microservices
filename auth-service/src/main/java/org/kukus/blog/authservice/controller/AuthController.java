package org.kukus.blog.authservice.controller;

import jakarta.validation.Valid;
import org.kukus.blog.authservice.dto.LoginRequest;
import org.kukus.blog.authservice.dto.RegisterRequest;
import org.kukus.blog.authservice.model.User;
import org.kukus.blog.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        User user = authService.login(request);
        return ResponseEntity.ok("Login successful for " + user.getUsername());
    }
}
