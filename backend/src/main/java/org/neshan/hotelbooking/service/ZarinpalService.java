package org.neshan.hotelbooking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.neshan.hotelbooking.config.ZarinpalConfig;
import org.neshan.hotelbooking.model.dto.ApiResponse;
import org.neshan.hotelbooking.model.dto.PaymentResponse;
import org.neshan.hotelbooking.model.dto.zarinpal.*;
import org.neshan.hotelbooking.model.entity.Booking;
import org.neshan.hotelbooking.model.enumeration.BookingStatus;
import org.neshan.hotelbooking.repository.BookingRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ZarinpalService {
    RestTemplate restTemplate;
    ZarinpalConfig zarinpalConfig;
    BookingRepository bookingRepository;

    public ApiResponse<PaymentResponse> requestPayment(Booking booking) {
        try {
            ZarinpalPaymentRequest request = ZarinpalPaymentRequest.builder()
                    .merchantId(zarinpalConfig.getMerchantId())
                    .amount((int) booking.getPrice())
                    .description("Payment for booking %d with paymentCode:%d".formatted(booking.getId(), booking.getPaymentCode()))
                    .callbackUrl(zarinpalConfig.getCallbackUrl())
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<ZarinpalPaymentRequest> entity = new HttpEntity<>(request, headers);

            ZarinpalPaymentResponse response = restTemplate.postForObject(zarinpalConfig.getRequestUrl(), entity, ZarinpalPaymentResponse.class);

            if (response != null && response.getData() != null && response.getData().isSuccess()) {
                booking.setAuthority(response.getData().getAuthority());
                bookingRepository.save(booking);

                PaymentResponse paymentResponse = PaymentResponse.builder()
                        .redirectUrl(response.getData().getPaymentUrl(zarinpalConfig.getGatewayUrl()))
                        .bookingId(booking.getId())
                        .paymentCode(booking.getPaymentCode())
                        .build();

                return ApiResponse.<PaymentResponse>builder()
                        .message("پرداخت با موفقیت انجام شد.")
                        .httpStatus(HttpStatus.OK)
                        .data(paymentResponse)
                        .build();
            } else {
                String errorMessage = response != null ?
                        "پرداخت ناموفق بود: " + response.getData().getMessage() :
                        "پرداخت ناموفق بود: پاسخی از درگاه پرداخت دریافت نشد.";
                log.error(errorMessage);

                PaymentResponse paymentResponse = PaymentResponse.builder()
                        .bookingId(booking.getId())
                        .paymentCode(booking.getPaymentCode())
                        .build();

                return ApiResponse.<PaymentResponse>builder()
                        .message(errorMessage)
                        .httpStatus(HttpStatus.PAYMENT_REQUIRED)
                        .data(paymentResponse)
                        .build();
            }
        } catch (Exception e) {
            log.error("Error initiating payment", e);
            return ApiResponse.<PaymentResponse>builder()
                    .message("خطا در انجام پرداخت")
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    public boolean verifyPayment(String authority, Booking booking) {
        try {
            ZarinpalVerificationRequest request = ZarinpalVerificationRequest.builder()
                    .merchantId(zarinpalConfig.getMerchantId())
                    .amount((int) booking.getPrice())
                    .authority(authority)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<ZarinpalVerificationRequest> entity = new HttpEntity<>(request, headers);

            ZarinpalVerificationResponse response = restTemplate.postForObject(
                    zarinpalConfig.getVerificationUrl(), entity, ZarinpalVerificationResponse.class);

            if (response != null && response.getData() != null && response.getData().isSuccess()) {
                booking.setRefId(response.getData().getRefId());
                booking.setStatus(BookingStatus.BOOKED);
                bookingRepository.save(booking);
                return true;
            } else {
                String errorMessage = response != null ?
                        "تایید پرداخت ناموفق بود: " + response.getData().getMessage() :
                        "تأیید پرداخت ناموفق بود: پاسخی از درگاه پرداخت دریافت نشد!";
                log.error(errorMessage);
                return false;
            }
        } catch (Exception e) {
            log.error("Error verifying payment", e);
            return false;
        }
    }
}
