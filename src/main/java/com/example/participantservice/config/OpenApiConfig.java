package com.example.participantservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Participant Service API")
                .version("1.0")
                .description("POC for Spring Boot 4.0 / Jakarta EE 11 integration with Oracle PL/SQL on WebSphere Liberty.")
                .contact(new Contact()
                    .name("Mark Lindros")
                    .email("mlindros@gmail.com")));
    }
}