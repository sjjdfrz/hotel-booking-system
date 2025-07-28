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
public class ZarinpalVerificationResponse {
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
        @JsonProperty("ref_id")
        Long refId;

        public boolean isSuccess() {
            return code != null && code == 100;
        }
    }
}