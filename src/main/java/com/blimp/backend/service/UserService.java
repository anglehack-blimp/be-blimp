package com.blimp.backend.service;

import com.blimp.backend.dto.RegisterUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(RegisterUserRequest request);

}
