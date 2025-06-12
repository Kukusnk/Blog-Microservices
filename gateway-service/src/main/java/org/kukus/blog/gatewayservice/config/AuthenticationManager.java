package org.kukus.blog.gatewayservice.config;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationManager.class);
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username;

        try {
            username = jwtUtil.getUserIdFromToken(authToken);
            log.info("username: {}", username);
        } catch (Exception e) {
            username = null;
            log.error("exception: ", e);
        }

        if (username != null && jwtUtil.validateToken(authToken)) {
            Claims claims = jwtUtil.getClaimsFromToken(authToken);
            log.info("claims: {}", claims);
            List<String> role = jwtUtil.getRolesFromToken(authToken);
            log.info("role: {}", role);
            List<SimpleGrantedAuthority> authorities = role.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities
            );

            return Mono.just(authenticationToken);
        } else {
            return Mono.empty();
        }
    }
}
