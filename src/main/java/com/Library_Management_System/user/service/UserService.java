package com.Library_Management_System.user.service;

import com.Library_Management_System.configuration.exception.BadRequestException;
import com.Library_Management_System.security.authentication.providers.UsernamePasswordAuthentication;
import com.Library_Management_System.security.authentication.utility.JwtTokenUtil;
import com.Library_Management_System.user.dto.LoginDto;
import com.Library_Management_System.user.dto.UserDto;
import com.Library_Management_System.user.entity.Token;
import com.Library_Management_System.user.entity.User;
import com.Library_Management_System.user.repository.UserRepositoryImpl;
import com.mongodb.client.result.UpdateResult;
import jakarta.annotation.PostConstruct;
import lombok.val;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import static com.Library_Management_System.security.authorization.ApplicationUserRole.ROLE_ADMIN;
import static com.Library_Management_System.security.authorization.ApplicationUserRole.ROLE_USER;

@Service
public class UserService {


    private final UserRepositoryImpl repo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public UserService(UserRepositoryImpl repo, PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public User addUser(UserDto userDto) {

        User user = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(ROLE_USER.name())
                .token(new Token())
                .build();
        return repo.addUser(user);
    }

    public User addAdmin(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(ROLE_ADMIN.name())
                .token(new Token())
                .build();
        return repo.addUser(user);
    }

    @PostConstruct
    private void addDefaultAdmin() {
        User user = User.builder()
                .name("admin")
                .surname("admin")
                .email("bookShop@gmail.com")
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(ROLE_ADMIN.name())
                .token(new Token())
                .build();
        if (!repo.doesExists(user.getUsername())) {
            repo.addUser(user);
        }
    }

    @PostConstruct
    private void addDefaultUser(){
        User user = User.builder()
                .name("user")
                .surname("user")
                .email("bookShop@gmail.com")
                .username("user")
                .password(passwordEncoder.encode("user"))
                .role(ROLE_USER.name())
                .token(new Token())
                .build();
        if (!repo.doesExists(user.getUsername())) {
            repo.addUser(user);
        }
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public User findUserByAccessToken(String accessToken) {
        return repo.findUserByAccessToken(accessToken);
    }

    public String login(LoginDto request) {
        if (request.getUsername() == null || request.getPassword() == null)
            throw new BadRequestException("Wrong credentials!");

        val authentication = new UsernamePasswordAuthentication(request.getUsername(), request.getPassword());
        Authentication a = authenticationManager.authenticate(authentication);


        String role = a.getAuthorities().stream().filter(grantedAuthority ->
                        grantedAuthority.getAuthority().startsWith("ROLE_"))
                .findFirst().map(GrantedAuthority::getAuthority).orElseThrow();

        String accessToken = jwtTokenUtil.generateToken(a.getName(), role, 2);
        String refreshToken = jwtTokenUtil.generateToken(a.getName(), role, 8);
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setIsRevoked(false);
        addToken(token, request.getUsername());

        return accessToken;
    }

    public UpdateResult revokeToken(String userId, String token) {
        return repo.revokeToken(userId, token);
    }

    public void addToken(Token token, String username) {
        repo.addToken(token, username);
    }

    public boolean doesExists(String username) {
        return repo.doesExists(username);
    }
}
