package org.neshan.hotelbooking.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("zarinpal")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ZarinpalConfig {
    String merchantId;
    String requestUrl;
    String verificationUrl;
    String gatewayUrl;
    String callbackUrl;
}
