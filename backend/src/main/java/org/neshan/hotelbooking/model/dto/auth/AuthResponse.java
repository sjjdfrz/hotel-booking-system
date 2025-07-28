package org.neshan.hotelbooking.model.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {
    String accessToken;
    String refreshToken;
    String mfaQrCodeImage;
    String secretKey;
}