package com.Library_Management_System.security.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ApplicationUserRole {
    ROLE_ADMIN(List.of("read", "write", "update", "delete")),
    ROLE_USER(List.of("read"));

    private final List<String> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {

        return getPermissions()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

}
