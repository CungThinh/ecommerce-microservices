package com.cungthinh.authservices.config;

import com.cungthinh.authservices.entity.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cungthinh.authservices.entity.UserEntity;
import com.cungthinh.authservices.repository.RoleRepository;
import com.cungthinh.authservices.repository.UserResipotory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ApplicationInitConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "org.postgresql.Driver")
    ApplicationRunner applicationRunner(UserResipotory userResipotory, RoleRepository roleRepository) {
        log.info("ApplicationRunner for PostgreSQL");
        return args -> {
            if (userResipotory.findByEmail("admin@gmail.com").isEmpty()) {
                HashSet<Role> roles = new HashSet<>();
                Role adminRole = roleRepository.findById("ADMIN")
                        .orElseThrow(() -> new RuntimeException("ADMIN role not found"));
                roles.add(adminRole);
                UserEntity user = UserEntity.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("dontwastetime"))
                        .roles(roles)
                        .build();
                userResipotory.save(user);
                log.warn("Tài khoản admin đã được tạo mặc định");
            }
        };
    }
}
