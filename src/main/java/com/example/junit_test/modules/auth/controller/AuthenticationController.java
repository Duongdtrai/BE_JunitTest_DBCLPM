package com.example.junit_test.modules.auth.controller;

import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.auth.dto.*;
import com.example.junit_test.modules.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth", description = "Authentication APIs")
@Hidden
public class AuthenticationController {
    @Autowired
    private final AuthenticationService service;


    @PostMapping("/sign-up")
    public ResponseEntity<SystemResponse<SignUpResponseDto>> register(
            @Valid @RequestBody RegisterRequestDto request, Errors errors
            ) {
        return service.register(request);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SystemResponse<AuthenticationResponseDto>> authenticate(
            @RequestBody AuthenticationRequestDto request
    ) {
        return service.login(request);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<SystemResponse<Boolean>> forgotPassword(
            @Valid @RequestBody ForgetPasswordDto request, Errors errors
    ) {
        return service.forgotPassword(request);
    }


    @PostMapping("/otp")
    public ResponseEntity<SystemResponse<Boolean>> otp(
            @Valid @RequestBody OtpDto request, Errors errors
    ) {
        return service.otp(request);
    }
}
