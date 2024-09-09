package com.Library_Management_System.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@AllArgsConstructor
public class UserDto {

    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
}
