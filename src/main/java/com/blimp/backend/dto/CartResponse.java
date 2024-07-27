package com.blimp.backend.dto;

import java.util.List;

public record CartResponse(List<ProductCartResponse> cartList) {
}
