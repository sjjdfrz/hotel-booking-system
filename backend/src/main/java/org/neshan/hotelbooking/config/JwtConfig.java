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
@ConfigurationProperties("jwt")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtConfig {
    String secretKey;
    Long expiration;
    Long refreshTokenExpiration;
}
