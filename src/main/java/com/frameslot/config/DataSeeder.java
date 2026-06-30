package com.frameslot.config;

import com.frameslot.domain.Role;
import com.frameslot.domain.User;
import com.frameslot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminName;
    private final String adminEmail;
    private final String adminPassword;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder,
                      @Value("${frameslot.admin.name}") String adminName,
                      @Value("${frameslot.admin.email}") String adminEmail,
                      @Value("${frameslot.admin.password}") String adminPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail(adminEmail)) {
            userRepository.save(new User(
                    adminName,
                    adminEmail,
                    passwordEncoder.encode(adminPassword),
                    "0000000000",
                    Role.ADMIN
            ));
        }
    }
}
