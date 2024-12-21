package com.example.personal_accounting.configs;

import com.example.personal_accounting.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        Long userId = null;
        String jwt;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtService.extractUsername(jwt);
                userId = jwtService.extractUserId(jwt);
            } catch (ExpiredJwtException e) {
                log.debug("Token lifetime has expired");
            } catch (SignatureException e) {
                log.debug("The signature is incorrect");
            }
        }

        if (username != null && userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    new CustomUserPrincipal(username, userId),
                    null,
                    null

            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Successful authentication for user: " + username);
        }
        filterChain.doFilter(request, response);
    }

    public record CustomUserPrincipal(String username, Long userId) {
        @Override
            public String toString() {
                return "CustomUserPrincipal{" +
                        "username='" + username + '\'' +
                        ", userId=" + userId +
                        '}';
            }
        }
}
