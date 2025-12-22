package com.omurkrmn.library_management.bootstrap;

import com.omurkrmn.library_management.entity.Role;
import com.omurkrmn.library_management.entity.User;
import com.omurkrmn.library_management.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {

        if(userRepository.findByUsername("admin").isEmpty()){
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin1234"));
            user.setRole(Role.ROLE_ADMIN);
            userRepository.save(user);

            log.info("Admin created");
        }

    }
}
