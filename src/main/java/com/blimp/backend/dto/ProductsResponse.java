package com.blimp.backend.dto;

import java.util.List;

public record ProductsResponse(List<ProductResponse> products) {
}
