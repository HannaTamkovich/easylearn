package com.easylearn.easylearn.security.auth.filter;

import com.easylearn.easylearn.security.auth.service.TokenStoreService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final TokenStoreService tokenStoreService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenOpt = Optional.ofNullable(request.getHeader("Authorization"));
        tokenOpt.ifPresent(token -> {
            var authentication = findAuthentication(token);
            authentication.ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));
        });
        filterChain.doFilter(request, response);
    }

    private Optional<Authentication> findAuthentication(String token) {
        var userOpt = tokenStoreService.findByToken(token);
        return userOpt.map(it -> new UsernamePasswordAuthenticationToken(it, null, it.getAuthorities()));
    }
}
