package com.blimp.backend.controller;

import com.blimp.backend.dto.BlimpResponse;
import com.blimp.backend.dto.RegisterUserRequest;
import com.blimp.backend.dto.UserResponse;
import com.blimp.backend.entity.User;
import com.blimp.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(
            path = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BlimpResponse<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return new BlimpResponse<>("OK");
    }

    @GetMapping(
            path = "/user/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BlimpResponse<UserResponse> get(User user) {
        var userResponse = userService.get(user);
        return new BlimpResponse<>(userResponse, null);
    }

}
