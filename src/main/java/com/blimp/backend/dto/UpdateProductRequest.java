package com.blimp.backend.dto;

public record UpdateProductRequest(
                String name,
                String description,
                String image,
                String video,
                Long price,
                Integer quantity) {
}
