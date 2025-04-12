package com.subhajit.microservices.user_service.dto;

import com.subhajit.microservices.user_service.model.Role;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
}
