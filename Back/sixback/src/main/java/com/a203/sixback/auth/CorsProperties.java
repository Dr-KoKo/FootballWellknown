package com.a203.sixback.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    private String AllowedOrigins;
    private String AllowedMethods;
    private String AllowedHeaders;
    private Long maxAge;
}
