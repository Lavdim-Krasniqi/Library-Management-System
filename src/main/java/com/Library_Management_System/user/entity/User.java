package com.Library_Management_System.user.entity;

import jakarta.annotation.sql.DataSourceDefinitions;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("User")
@Data
@Builder
public class User {

    public static final String USERNAME = "username";
    public static final String ID = "id";
    public static final String PASSWORD = "password";
    public static final String ROLE = "role";
    public static final String EMAIL = "email";
    public static final String ACCESS_TOKEN = "token.accessToken";
    public static final String REFRESH_TOKEN = "token.refreshToken";
    public static final String TOKEN_IS_REVOKED = "token.isRevoked";
    public static final String TOKEN= "token";



    private String id;
    private String name;
    private String surname;
    private String email;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String role;
    private Token token;
}
