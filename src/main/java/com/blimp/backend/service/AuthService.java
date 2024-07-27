package com.blimp.backend.service;

import com.blimp.backend.dto.LoginUserRequest;

public interface AuthService {

    String getLoginToken(LoginUserRequest request);

}
