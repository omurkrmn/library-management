package com.omurkrmn.library_management.service;

import com.omurkrmn.library_management.dto.request.user.LoginRequest;
import com.omurkrmn.library_management.dto.request.user.RegisterRequest;
import com.omurkrmn.library_management.dto.response.UserResponse;
import com.omurkrmn.library_management.entity.Role;
import com.omurkrmn.library_management.entity.User;
import com.omurkrmn.library_management.exception.BusinessException;
import com.omurkrmn.library_management.messasing.event.LibraryEvent;
import com.omurkrmn.library_management.messasing.producer.LibraryEventProducer;
import com.omurkrmn.library_management.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LibraryEventProducer libraryEventProducer;
    private final ModelMapper modelMapper;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, LibraryEventProducer libraryEventProducer, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.libraryEventProducer = libraryEventProducer;
        this.modelMapper = modelMapper;
    }

    public UserResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            log.error("Username is already in use");
            throw new BusinessException("Username is already in use");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);

        User savedUser = userRepository.save(user);
        log.info("User registered {}", savedUser.getUsername());

        libraryEventProducer.sendMessage(
                new LibraryEvent(
                        "USER_REGISTERED",
                        savedUser.getUsername(),
                        LocalDateTime.now()
                )
        );

        return modelMapper.map(savedUser, UserResponse.class);
    }

    public UserResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException("Username not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Invalid password");
        }

        log.info("Logged in {}", user.getUsername());

        libraryEventProducer.sendMessage(
                new LibraryEvent(
                        "USER_LOGIN",
                        user.getUsername(),
                        LocalDateTime.now()
                )
        );

        return modelMapper.map(user, UserResponse.class);
    }
}
