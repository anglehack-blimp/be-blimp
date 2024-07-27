package com.blimp.backend.dto;

import org.springframework.web.multipart.MultipartFile;

public record CreateProductRequest(
                String name,
                String description,
                MultipartFile image,
                MultipartFile video,
                Long price,
                Integer quantity) {
}
