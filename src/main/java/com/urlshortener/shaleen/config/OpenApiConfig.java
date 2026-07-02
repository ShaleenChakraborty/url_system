package com.urlshortener.shaleen.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI urlShortenerAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("URL Shortener API")

                        .description("""
                                A production-ready URL Shortener built using Spring Boot.

                                Features:
                                • URL Shortening
                                                               
                                • URL Redirection
                                                               
                                • Duplicate URL Detection
                                                               
                                • Click Analytics
                                                               
                                • Request Validation
                                                               
                                • PostgreSQL Storage
                                                               
                                Built using:
                                                               
                                 Spring Boot
                                                               
                                 Spring MVC
                                                               
                                 Spring Data JPA
                                                               
                                 Hibernate
                                                               
                                 PostgreSQL
                                                               
                                 OpenAPI
                                                               
                                 Bean Validation

                                Developed by Shaleen Chakraborty
                                """)

                        .version("1.0.0")

                        .contact(new Contact()

                                .name("Shaleen Chakraborty")
                                .email("shaleenece@example.com")
                                .url("https://github.com/ShaleenChakraborty"))

                        .license(new License()

                                .name("MIT")));
    }

}