package org.neshan.hotelbooking.model.dto.zarinpal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ZarinpalPaymentRequest {
    @JsonProperty("merchant_id")
    String merchantId;
    Integer amount;
    String description;
    @JsonProperty("callback_url")
    String callbackUrl;
}