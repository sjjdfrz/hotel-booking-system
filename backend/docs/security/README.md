# Security Documentation

This document describes the security implementation in the Hotel Booking System.

## Overview

The Hotel Booking System implements a robust security architecture using Spring Security with JWT (JSON Web Token) authentication and two-factor authentication (2FA).

## Authentication Flow

1. **Registration**:
   - User registers with email, password, and personal information
   - A secret key is generated for 2FA
   - User must verify their identity using OTP before completing registration

2. **Login**:
   - User provides email and password
   - If credentials are valid, the system requires OTP verification
   - After successful OTP verification, JWT tokens (access and refresh) are issued

3. **API Access**:
   - Protected endpoints require a valid JWT token in the Authorization header
   - The token is validated for authenticity and expiration
   - User details are extracted from the token for authorization checks

## Components

### JWT Service

The `JwtService` handles JWT token operations:

- Token generation
- Token validation
- Token parsing to extract user information
- Token expiration management

Configuration parameters in `application.yaml`:
```yaml
jwt:
  secret-key: "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
  expiration: 86400000 # a day
  refresh-token-expiration: 604800000 # 7 days
```

### Two-Factor Authentication Service

The `TwoFactorAuthService` implements 2FA using Time-based One-Time Password (TOTP):

- Generates secret keys for users
- Validates OTP codes
- Manages OTP expiration

### JWT Authentication Filter

The `JwtAuthenticationFilter` intercepts incoming requests:

- Extracts JWT token from the Authorization header
- Validates the token
- Sets the authenticated user in the SecurityContext
- Allows or denies access based on token validity

### Security Configuration

The `SecurityConfig` class configures Spring Security:

- Defines security filter chain
- Configures authentication provider
- Sets up CORS and CSRF protection
- Defines authorization rules for different endpoints
- Configures exception handling for authentication failures

## Endpoint Security

| Endpoint | Authentication Required | Roles Allowed |
|----------|-------------------------|---------------|
| `/auth/**` | No | All |
| `/api/hotels` | No | All |
| `/api/rooms` | Yes | USER, ADMIN |
| `/api/bookings` | Yes | USER, ADMIN |
| `/api/bookings/{id}` | Yes | USER, ADMIN |
| `/api/payments/**` | Yes | USER, ADMIN |
| `/api/payment/callback` | No | All |
| `/api/users/me/**` | Yes | USER, ADMIN |

## Password Security

- Passwords are encrypted using BCrypt before storage
- Password complexity requirements are enforced during registration
- Failed login attempts are monitored

## CORS Configuration

Cross-Origin Resource Sharing (CORS) is configured to allow requests only from the frontend application:

```java
@CrossOrigin(origins = "http://localhost")
```

## Security Best Practices

1. **Token-based Authentication**: Using JWT for stateless authentication
2. **Two-Factor Authentication**: Adding an extra layer of security
3. **HTTPS**: All communication should be over HTTPS
4. **Password Encryption**: Passwords are never stored in plain text
5. **Role-Based Access Control**: Restricting access based on user roles
6. **Input Validation**: All user inputs are validated
7. **CORS Protection**: Preventing cross-origin attacks
8. **Short-lived Tokens**: Access tokens expire after a day
9. **Refresh Tokens**: Longer-lived tokens for obtaining new access tokens
10. **Secure Headers**: HTTP security headers are configured