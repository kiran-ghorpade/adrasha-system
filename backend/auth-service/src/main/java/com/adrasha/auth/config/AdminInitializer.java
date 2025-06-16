package com.adrasha.auth.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.adrasha.auth.model.AccountStatus;
import com.adrasha.auth.model.Role;
import com.adrasha.auth.model.User;
import com.adrasha.auth.repository.UserRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

	@Value("admin")
    private String username;

    @Value("admin@123")
    private String password;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByRolesContaining(Role.ADMIN)) {
            User admin = new User();
            admin.setUsername(username);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRoles(Set.of(Role.USER,Role.ADMIN));
            admin.setStatus(AccountStatus.APPROVED);
            userRepository.save(admin);
        }
    }
}
