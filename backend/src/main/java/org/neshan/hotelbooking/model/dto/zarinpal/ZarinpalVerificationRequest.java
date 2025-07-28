package org.neshan.hotelbooking.model.dto.zarinpal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ZarinpalVerificationRequest {
    @JsonProperty("merchant_id")
    String merchantId;
    Integer amount;
    String authority;
}