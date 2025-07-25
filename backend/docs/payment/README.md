# Payment Integration Documentation

This document describes the payment integration in the Hotel Booking System.

## Overview

The Hotel Booking System integrates with Zarinpal, an Iranian payment gateway, to process payments for hotel bookings. The integration follows a standard payment flow:

1. User creates a booking
2. User initiates payment
3. User is redirected to Zarinpal payment page
4. User completes payment on Zarinpal
5. User is redirected back to the application
6. Application verifies payment status
7. Booking status is updated based on payment result

## Configuration

Zarinpal integration is configured in `application.yaml`:

```yaml
zarinpal:
  merchant-id: "2940b83c-a828-47d5-815f-0af13ab71b19"
  request-url: "https://sandbox.zarinpal.com/pg/v4/payment/request.json"
  verification-url: "https://sandbox.zarinpal.com/pg/v4/payment/verify.json"
  gateway-url: "https://sandbox.zarinpal.com/pg/StartPay/"
  callback-url: "http://localhost:8080/api/payment/callback"
```

- `merchant-id`: Your Zarinpal merchant ID
- `request-url`: URL for initiating payment requests
- `verification-url`: URL for verifying payment status
- `gateway-url`: Base URL for redirecting users to the payment page
- `callback-url`: URL where Zarinpal will redirect users after payment

## Components

### ZarinpalConfig

The `ZarinpalConfig` class loads configuration from `application.yaml` and provides it to other components.

### ZarinpalService

The `ZarinpalService` handles communication with the Zarinpal API:

- Initiates payment requests
- Verifies payment status
- Handles API responses

### PaymentService

The `PaymentService` orchestrates the payment process:

- Retrieves booking information
- Calls ZarinpalService to initiate payment
- Updates booking status based on payment result
- Handles payment callbacks

### PaymentController

The `PaymentController` exposes payment-related endpoints:

- `/api/payments/{paymentCode}`: Initiates payment for a booking
- `/api/payment/callback`: Handles callbacks from Zarinpal

## Payment Flow

### 1. Booking Creation

When a user creates a booking, it is initially saved with a status of `PENDING` and a unique `paymentCode` is generated.

### 2. Payment Initiation

1. User requests to pay for a booking using the `paymentCode`
2. `PaymentService` retrieves the booking details
3. `ZarinpalService` sends a payment request to Zarinpal with:
   - Amount
   - Description
   - Callback URL
   - Metadata
4. Zarinpal responds with an `Authority` token
5. User is redirected to Zarinpal payment page with the `Authority` token

### 3. Payment Processing

1. User completes payment on Zarinpal's platform
2. Zarinpal redirects the user back to the callback URL with:
   - `Authority`: The payment authority token
   - `Status`: The payment status (OK or NOK)

### 4. Payment Verification

1. `PaymentController` receives the callback
2. `PaymentService` verifies the payment with Zarinpal
3. If payment is successful:
   - Booking status is updated to `BOOKED`
   - Payment reference ID is stored
   - User is redirected to success page
4. If payment fails:
   - Booking remains in `PENDING` state
   - User is redirected to failure page

## Data Models

### ZarinpalPaymentRequest

```java
public class ZarinpalPaymentRequest {
    private String merchantId;
    private int amount;
    private String description;
    private String callbackUrl;
    private String metadata;
}
```

### ZarinpalPaymentResponse

```java
public class ZarinpalPaymentResponse {
    private int code;
    private String message;
    private String authority;
    private String feeType;
    private int fee;
}
```

### ZarinpalVerificationRequest

```java
public class ZarinpalVerificationRequest {
    private String merchantId;
    private int amount;
    private String authority;
}
```

### ZarinpalVerificationResponse

```java
public class ZarinpalVerificationResponse {
    private int code;
    private String message;
    private long refId;
    private String cardPan;
    private String cardHash;
    private String feeType;
    private int fee;
}
```

## Error Handling

The payment integration includes error handling for various scenarios:

1. **Invalid Payment Code**: If the payment code doesn't match any booking
2. **Already Paid**: If the booking has already been paid
3. **Payment Initiation Failure**: If Zarinpal rejects the payment request
4. **Payment Verification Failure**: If payment verification fails
5. **Network Issues**: If communication with Zarinpal fails
