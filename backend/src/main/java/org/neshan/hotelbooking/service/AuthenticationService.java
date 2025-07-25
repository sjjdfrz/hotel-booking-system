package org.neshan.hotelbooking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.neshan.hotelbooking.model.dto.ApiResponse;
import org.neshan.hotelbooking.model.dto.auth.AuthResponse;
import org.neshan.hotelbooking.model.dto.auth.LoginRequest;
import org.neshan.hotelbooking.model.dto.auth.RegisterRequest;
import org.neshan.hotelbooking.model.dto.auth.VerifyOtpRequest;
import org.neshan.hotelbooking.model.entity.User;
import org.neshan.hotelbooking.model.enumeration.Role;
import org.neshan.hotelbooking.repository.UserRepository;
import org.neshan.hotelbooking.security.JwtService;
import org.neshan.hotelbooking.security.TwoFactorAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    JwtService jwtService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    TwoFactorAuthService twoFactorAuthService;
    AuthenticationManager authenticationManager;

    public ApiResponse<AuthResponse> register(RegisterRequest request) {
        // Check if a user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.<AuthResponse>builder()
                    .message("این ایمیل قبلاً ثبت شده است!")
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        }

        // Create user
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(Role.USER)
                .build();

        // Generate MFA secret
        user.setSecretKey(twoFactorAuthService.generateSecretKey());

        // Save user
        userRepository.save(user);

        String qrCodeUrl = twoFactorAuthService.getGoogleAuthenticatorBarCode(user.getSecretKey(), user.getEmail());

        AuthResponse authResponse = AuthResponse.builder()
                .mfaQrCodeImage(twoFactorAuthService.generateQrCodeImage(qrCodeUrl))
                .secretKey(user.getSecretKey())
                .build();

        return ApiResponse.<AuthResponse>builder()
                .data(authResponse)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public ApiResponse<AuthResponse> login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Get user
        var userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            return ApiResponse.<AuthResponse>builder()
                    .message("کاربری با این ایمیل یافت نشد!")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ApiResponse.<AuthResponse>builder()
                .message("احراز هویت با موفقیت انجام شد. لطفاً کد یک‌بار مصرف را وارد کنید.")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ApiResponse<AuthResponse> verifyOtp(VerifyOtpRequest request) {
        // Get user
        var userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            return ApiResponse.<AuthResponse>builder()
                    .message("کاربری با این ایمیل یافت نشد!")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }

        User user = userOptional.get();

        // Verify OTP
        if (!twoFactorAuthService.validateOtp(user.getSecretKey(), request.getCode())) {
            return ApiResponse.<AuthResponse>builder()
                    .message("کد یک‌بار مصرف نامعتبر است!")
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        // Generate tokens
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Return tokens
        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ApiResponse.<AuthResponse>builder()
                .data(authResponse)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
