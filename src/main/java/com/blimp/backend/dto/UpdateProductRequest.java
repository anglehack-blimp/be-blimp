package com.blimp.backend.dto;

import org.springframework.web.multipart.MultipartFile;

public record UpdateProductRequest(
        String name,
        String description,
        MultipartFile image,
        MultipartFile video,
        Long price,
        Integer quantity) {
}
