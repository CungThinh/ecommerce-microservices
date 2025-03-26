package com.cungthinh.notificationservices.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "mail")
public class ProviderConfig {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private Boolean debug;
}
