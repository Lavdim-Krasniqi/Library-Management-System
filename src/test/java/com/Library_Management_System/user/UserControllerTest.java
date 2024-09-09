package com.Library_Management_System.user;

import com.Library_Management_System.baseConfiguration.BaseTest;
import com.Library_Management_System.security.authorization.ApplicationUserRole;
import com.Library_Management_System.user.dto.LoginDto;
import com.Library_Management_System.user.dto.UserDto;
import com.Library_Management_System.user.entity.User;
import com.Library_Management_System.user.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest extends BaseTest {

    @Autowired
    UserService userService;

    @Test
    void addUser() {
        UserDto requestBody =
                UserDto.builder()
                        .name("test")
                        .surname("test1")
                        .username("test12")
                        .email("test123")
                        .password("test")
                        .build();

        User user = requestOne("/user/addUser", HttpMethod.POST, null, null, requestBody, User.class);

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("test12");
        assertThat(user.getRole()).isEqualTo(ApplicationUserRole.ROLE_USER.toString());
    }

    @Test
    void addAdmin() {
        super.login(new LoginDto("admin", "admin"));
        UserDto requestBody =
                UserDto.builder()
                        .name("testAdmin")
                        .surname("testAdmin")
                        .username("testAdmin")
                        .email("testAdmin")
                        .password("testAdmin")
                        .build();
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        User user = requestOne("/user/addAdmin", HttpMethod.POST, httpHeaders, null, requestBody, User.class);

        assertThat(user).isNotNull();
        assertThat(user.getRole()).isEqualTo(ApplicationUserRole.ROLE_ADMIN.toString());
    }

    @Test
    void revokeToken() {
        super.login(new LoginDto("admin", "admin"));
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("Authorization", token);

        User user = userService.findByUsername("admin");

        requestOne("/user/revokeToken/" + user.getId() + "/" + token.substring(7), HttpMethod.PUT, httpHeaders, null, null, Object.class);

        assertThat(userService.findByUsername("admin").getToken().getIsRevoked()).isTrue();


    }
}