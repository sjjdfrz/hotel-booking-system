package org.neshan.hotelbooking.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    String redirectUrl;
    Long bookingId;
    Long paymentCode;
}