package com.subhajit.microservices.user_service.dto;

import lombok.Data;

@Data
public class UserUpdateRequestDTO {
    private String email;
    private String firstName;
    private String lastName;
}
