package com.example.auth;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class AuthResponse {

    private String jwtToken;
}
