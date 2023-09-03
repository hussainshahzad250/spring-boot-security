package com.hussain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hussain.entity.ApplicationUser;
import com.hussain.entity.Role;
import com.hussain.repository.RoleRepository;
import com.hussain.repository.UserRepository;

@SpringBootApplication
public class AuthenticatedBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticatedBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode) {
        return args -> {
            if (roleRepository.findByAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            Role userRole = roleRepository.save(new Role("USER"));

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            userRepository.save(new ApplicationUser(1, "admin", passwordEncode.encode("admin"), adminRoles));

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            userRepository.save(new ApplicationUser(2, "user", passwordEncode.encode("user"), userRoles));
        };
    }
}
