package com.blimp.backend.dto;

import java.util.List;

public record CartRequest(List<ProductCartRequest> cartList) { }
