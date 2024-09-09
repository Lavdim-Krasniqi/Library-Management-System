package com.Library_Management_System.security.authorization;

import com.Library_Management_System.configuration.exception.NoAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class MethodAuthorization {

    public boolean hasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String role = authentication.getAuthorities()
                .stream()
                .findFirst().orElseThrow().getAuthority();

        ApplicationUserRole applicationUserRole = ApplicationUserRole.valueOf(role);

        if (applicationUserRole.getAuthorities()
                .stream().anyMatch(simpleGrantedAuthority -> simpleGrantedAuthority.getAuthority().equals(authority)))
            return true;

        throw new NoAccessException("User has no access to this resource!");
    }

    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role)))
            return true;
        throw new NoAccessException("User has no access to this resource!");
    }
}
