package com.blimp.backend.controller;

import com.blimp.backend.dto.BlimpResponse;
import com.blimp.backend.dto.LoginUserRequest;
import com.blimp.backend.dto.TokenResponse;
import com.blimp.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BlimpResponse<TokenResponse> login(@RequestBody LoginUserRequest request) {
        var jwtToken = authService.getLoginToken(request);
        return new BlimpResponse<>(new TokenResponse(jwtToken));
    }

}
