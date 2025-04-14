package com.subhajit.microservices.user_service.dto;

import lombok.Data;

@Data
public class AuthenticationCredentialsRequestDTO {
    private String username;
    private String password;
}
