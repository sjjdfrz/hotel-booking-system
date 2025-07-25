package org.neshan.hotelbooking.component;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanPool {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My Hotel Booking API Documentation")
                        .version("1.0")
                        .description("API documentation for My Hotel Booking Application"));
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
