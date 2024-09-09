package com.Library_Management_System.security.authentication.filters;


import com.Library_Management_System.configuration.exception.RequiredTokenException;
import com.Library_Management_System.security.authentication.providers.TokenAuthentication;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {


    private final AuthenticationManager manager;
    private final HandlerExceptionResolver exceptionResolver;

    public TokenFilter(AuthenticationManager manager, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.manager = manager;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token == null) {
            exceptionResolver.resolveException(request, response, null, new RequiredTokenException("Token shouldn't be null"));
        } else {
            token = token.substring(7);
            val authentication = new TokenAuthentication(token, null);
            try {
                Authentication a = manager.authenticate(authentication);

                SecurityContextHolder.getContext().setAuthentication(a);

                if (a.getDetails() != null) response.setHeader("Authorization", (String) a.getDetails());

                filterChain.doFilter(request, response);
            } catch (BadCredentialsException | ExpiredJwtException e) {
                exceptionResolver.resolveException(request, response, null, e);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.equals("/user/login") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars/") ||
                path.startsWith("/v2/api-docs") ||
                path.equals("/user/addUser");
    }

}
