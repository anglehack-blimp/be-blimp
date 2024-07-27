package com.blimp.backend.service.impl;

import com.blimp.backend.dto.RegisterUserRequest;
import com.blimp.backend.dto.UserResponse;
import com.blimp.backend.entity.User;
import com.blimp.backend.entity.UserRole;
import com.blimp.backend.entity.UserStatus;
import com.blimp.backend.repository.UserRepository;
import com.blimp.backend.service.UserService;
import com.blimp.backend.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j @RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ValidationService validationService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("user with username = '" + username + "' is not found"));
    }

    @Override
    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validate(request);

        if (userRepository.existsById(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already registered");
        }

        var user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(UserRole.valueOf(request.role()));
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        log.info("new user created with username = '{}'", user.getUsername());
    }

    @Override
    public UserResponse get(User user) {
        return new UserResponse(user.getUsername());
    }
}
