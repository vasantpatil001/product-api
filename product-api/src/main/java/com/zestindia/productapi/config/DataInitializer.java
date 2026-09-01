package com.zestindia.productapi.config;

import com.zestindia.productapi.entity.AppUser;
import com.zestindia.productapi.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createAdminUser(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (appUserRepository.findByUsername("admin").isEmpty()) {

                AppUser admin = new AppUser();

                admin.setUsername("admin");
                admin.setPassword(
                        passwordEncoder.encode("admin123")
                );
                admin.setRole("ADMIN");

                appUserRepository.save(admin);

                System.out.println("Default ADMIN user created");
            }
        };
    }
}