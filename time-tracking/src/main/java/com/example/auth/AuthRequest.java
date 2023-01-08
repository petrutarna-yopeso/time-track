package com.example.auth;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class AuthRequest {

    @Email
    private String email;
}
