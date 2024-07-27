package com.blimp.backend.service.impl;

import com.blimp.backend.dto.LoginUserRequest;
import com.blimp.backend.security.JwtTokenUtils;
import com.blimp.backend.service.AuthService;
import com.blimp.backend.service.UserService;
import com.blimp.backend.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final ValidationService validationService;

    private final JwtTokenUtils jwtTokenUtils;

    private final AuthenticationManager authenticationManager;

    @Override
    public String getLoginToken(LoginUserRequest request) {
        validationService.validate(request);

        authenticate(request.username(), request.password());

        var user = userService.loadUserByUsername(request.username());
        return jwtTokenUtils.generateToken(user);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username or password wrong");
        }
    }

}
