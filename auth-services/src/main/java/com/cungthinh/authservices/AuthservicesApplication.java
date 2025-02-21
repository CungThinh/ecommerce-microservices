package com.cungthinh.authservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthservicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthservicesApplication.class, args);
    }
}
