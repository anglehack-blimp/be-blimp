package com.blimp.backend.service;

import com.blimp.backend.dto.RegisterUserRequest;
import com.blimp.backend.dto.UserResponse;
import com.blimp.backend.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(RegisterUserRequest request);

    UserResponse get(User user);

}
