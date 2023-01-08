package com.example.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/auth"})
@RequiredArgsConstructor
@Slf4j
public class AuthController {


    private  final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthResponse> auhtorize(@Valid @RequestBody AuthRequest req) {
        try {
            return ResponseEntity.ok(authService.authorize(req));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
