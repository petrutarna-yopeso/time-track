package com.example.auth;

import javax.security.sasl.AuthenticationException;

public interface AuthService {

    Boolean validateJwtToken(String jwtAuthToken) throws AuthenticationException;

    AuthResponse authorize(AuthRequest req) throws Exception;
}
