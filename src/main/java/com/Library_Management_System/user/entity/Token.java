package com.Library_Management_System.user.entity;

import lombok.Data;

@Data
public class Token {

    String accessToken;
    String refreshToken;
    Boolean isRevoked;
}
