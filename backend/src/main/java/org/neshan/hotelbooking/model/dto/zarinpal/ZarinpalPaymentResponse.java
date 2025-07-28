package org.neshan.hotelbooking.model.dto.zarinpal;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ZarinpalPaymentResponse {
    Data data;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Data {
        Integer code;
        String message;
        String authority;

        public boolean isSuccess() {
            return code != null && code == 100;
        }

        public String getPaymentUrl(String gatewayUrl) {
            if (isSuccess() && authority != null && !authority.isEmpty()) {
                return gatewayUrl + authority;
            }
            return null;
        }
    }
}