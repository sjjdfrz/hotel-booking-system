package org.neshan.hotelbooking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.neshan.hotelbooking.model.dto.ApiResponse;
import org.neshan.hotelbooking.model.dto.auth.AuthResponse;
import org.neshan.hotelbooking.model.dto.auth.LoginRequest;
import org.neshan.hotelbooking.model.dto.auth.RegisterRequest;
import org.neshan.hotelbooking.model.dto.auth.VerifyOtpRequest;
import org.neshan.hotelbooking.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication")
@CrossOrigin(origins = "http://localhost")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        ApiResponse<AuthResponse> response = authenticationService.register(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        ApiResponse<AuthResponse> response = authenticationService.login(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request
    ) {
        ApiResponse<AuthResponse> response = authenticationService.verifyOtp(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}