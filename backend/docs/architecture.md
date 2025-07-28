# System Architecture

This document provides an overview of the Hotel Booking System architecture.

## High-Level Architecture

```
+------------------+     +------------------+     +------------------+
|                  |     |                  |     |                  |
|  Client          |     |  Hotel Booking   |     |  PostgreSQL      |
|  Application     |<--->|  API Server      |<--->|  Database        |
|  (Frontend)      |     |  (Spring Boot)   |     |                  |
|                  |     |                  |     |                  |
+------------------+     +--------^---------+     +------------------+
                                  |
                                  |
                                  v
                         +------------------+
                         |                  |
                         |  Zarinpal        |
                         |  Payment Gateway |
                         |                  |
                         +------------------+
```

## Component Architecture

```
+------------------------------------------------------+
|                                                      |
|                  Spring Boot Application             |
|                                                      |
+------------------------------------------------------+
|                                                      |
|  +----------------+  +----------------+  +---------+ |
|  |                |  |                |  |         | |
|  | Controllers    |  | Services       |  | Models  | |
|  |                |  |                |  |         | |
|  +----------------+  +----------------+  +---------+ |
|                                                      |
|  +----------------+  +----------------+  +---------+ |
|  |                |  |                |  |         | |
|  | Repositories   |  | Security       |  | Config  | |
|  |                |  |                |  |         | |
|  +----------------+  +----------------+  +---------+ |
|                                                      |
+------------------------------------------------------+
```

## Layered Architecture

The application follows a standard layered architecture:

```
+------------------------------------------------------+
|                                                      |
|                  Presentation Layer                  |
|                  (Controllers)                       |
|                                                      |
+------------------------------------------------------+
|                                                      |
|                  Business Logic Layer                |
|                  (Services)                          |
|                                                      |
+------------------------------------------------------+
|                                                      |
|                  Data Access Layer                   |
|                  (Repositories)                      |
|                                                      |
+------------------------------------------------------+
|                                                      |
|                  Database Layer                      |
|                  (PostgreSQL)                        |
|                                                      |
+------------------------------------------------------+
```

## Component Descriptions

### Controllers
- **AuthController**: Handles authentication requests
- **HotelController**: Manages hotel-related operations
- **RoomController**: Handles room-related operations
- **BookingController**: Manages booking operations
- **PaymentController**: Processes payments
- **UserController**: Handles user-specific operations

### Services
- **AuthenticationService**: Implements authentication logic
- **HotelService**: Implements hotel-related business logic
- **RoomService**: Implements room-related business logic
- **BookingService**: Implements booking-related business logic
- **PaymentService**: Implements payment processing logic
- **ZarinpalService**: Integrates with Zarinpal payment gateway

### Repositories
- **UserRepository**: Data access for users
- **HotelRepository**: Data access for hotels
- **RoomRepository**: Data access for rooms
- **BookingRepository**: Data access for bookings

### Security Components
- **JwtService**: Handles JWT token operations
- **JwtAuthenticationFilter**: Intercepts and processes JWT tokens
- **TwoFactorAuthService**: Implements two-factor authentication
- **SecurityConfig**: Configures Spring Security

## Data Flow

### Booking Flow
```
User -> BookingController -> BookingService -> RoomRepository/BookingRepository -> Database
```

### Payment Flow
```
User -> PaymentController -> PaymentService -> ZarinpalService -> Zarinpal API
                                            -> BookingRepository -> Database
```

### Authentication Flow
```
User -> AuthController -> AuthenticationService -> UserRepository -> Database
                                               -> JwtService
                                               -> TwoFactorAuthService
```