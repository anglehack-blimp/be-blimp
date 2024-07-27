package com.blimp.backend.controller;

import com.blimp.backend.dto.BlimpResponse;
import com.blimp.backend.dto.CartRequest;
import com.blimp.backend.dto.CartResponse;
import com.blimp.backend.entity.User;
import com.blimp.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping()
    public BlimpResponse<CartResponse> addToCart(@RequestBody CartRequest request, User user) {
        var response = cartService.addToCart(request, user);
        return new BlimpResponse<>(response);
    }

}
