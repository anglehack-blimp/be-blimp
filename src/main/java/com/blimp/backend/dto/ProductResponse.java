package com.blimp.backend.dto;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String image,
        String video,
        Long price,
        Integer quantity) {
}
