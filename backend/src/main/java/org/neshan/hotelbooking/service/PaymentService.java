package org.neshan.hotelbooking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.neshan.hotelbooking.model.dto.ApiResponse;
import org.neshan.hotelbooking.model.dto.PaymentResponse;
import org.neshan.hotelbooking.model.entity.Booking;
import org.neshan.hotelbooking.model.enumeration.BookingStatus;
import org.neshan.hotelbooking.repository.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
    ZarinpalService zarinpalService;
    BookingRepository bookingRepository;

    public ApiResponse<PaymentResponse> initiatePayment(Long paymentCode) {
        var bookingOptional = bookingRepository.findByPaymentCode(paymentCode);
        if (bookingOptional.isEmpty()) {
            return ApiResponse.<PaymentResponse>builder()
                    .message("رزروی یافت نشد!")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }

        Booking booking = bookingOptional.get();

        if (booking.getStatus() != BookingStatus.PENDING) {
            return ApiResponse.<PaymentResponse>builder()
                    .message("وضعیت رزرو در حالت انتظار نیست!")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        // Reset expiration time to give user more time to complete payment
        booking.setPendingExpiresAt(LocalDateTime.now().plusMinutes(15));
        bookingRepository.save(booking);

        return zarinpalService.requestPayment(booking);
    }

    public boolean verifyPayment(String authority, String status) {
        if (!"OK".equalsIgnoreCase(status)) {
            log.error("Payment failed with status: {}", status);
            return false;
        }

        var bookingOptional = bookingRepository.findByAuthority(authority);
        if (bookingOptional.isEmpty()) {
            log.error("Booking not found with authority: {}", authority);
            return false;
        }

        Booking booking = bookingOptional.get();

        if (booking.getStatus() != BookingStatus.PENDING) {
            log.error("Booking is not in PENDING state: {}", booking.getStatus());
            return false;
        }

        return zarinpalService.verifyPayment(authority, booking);
    }
}
