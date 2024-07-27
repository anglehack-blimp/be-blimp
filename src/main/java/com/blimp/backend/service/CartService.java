package com.blimp.backend.service;

import com.blimp.backend.dto.CartRequest;
import com.blimp.backend.dto.CartResponse;
import com.blimp.backend.entity.User;

public interface CartService {

    CartResponse addToCart(CartRequest request, User user);

}
