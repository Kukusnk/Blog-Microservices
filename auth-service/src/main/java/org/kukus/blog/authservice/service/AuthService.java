package org.kukus.blog.authservice.service;

import lombok.RequiredArgsConstructor;
import org.kukus.blog.authservice.configure.JwtUtil;
import org.kukus.blog.authservice.dto.LoginRequest;
import org.kukus.blog.authservice.dto.RegisterRequest;
import org.kukus.blog.authservice.model.User;
import org.kukus.blog.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singletonList("USER")); // Default role for new users

        userRepository.save(user);
        return "User registered";
    }

    public String login(LoginRequest request) {
        System.out.println("EMAIL =================== "+request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email, user not found"));
        logger.info("User found with username: {}", user.getUsername());

        String userPassword = user.getPassword();
        String requestPassword = passwordEncoder.encode(request.getPassword());

        System.out.println("User: " + user);
        System.out.println("Password request: " + requestPassword);
        System.out.println("Password user: " + userPassword);
        System.out.printf(userPassword.equals(requestPassword) ? "Password Match" : "Password Mismatch");
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password does not match");
        }

        return jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRoles());
    }
}

