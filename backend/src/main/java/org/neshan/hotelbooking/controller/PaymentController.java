package org.neshan.hotelbooking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.neshan.hotelbooking.model.dto.ApiResponse;
import org.neshan.hotelbooking.model.dto.PaymentResponse;
import org.neshan.hotelbooking.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Payments")
@CrossOrigin(origins = "http://localhost")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;

    @GetMapping("/payments/{paymentCode}")
    public ResponseEntity<ApiResponse<PaymentResponse>> initiatePayment(@PathVariable("paymentCode") Long paymentCode) {
        ApiResponse<PaymentResponse> response = paymentService.initiatePayment(paymentCode);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/payment/callback")
    public RedirectView verifyPayment(
            @RequestParam("Authority") String authority,
            @RequestParam("Status") String status) {

        log.info("Payment callback received: Authority={}, Status={}", authority, status);

        boolean verified = paymentService.verifyPayment(authority, status);

        String redirectUrl = verified ?
                "http://localhost/payment-success":
                "http://localhost/payment-failure";

        return new RedirectView(redirectUrl);
    }
}
