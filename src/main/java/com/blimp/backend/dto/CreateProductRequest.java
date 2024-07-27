package com.blimp.backend.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public record CreateProductRequest(
        @NotBlank @Size(max = 255) String name,

        @NotBlank String description,

        @NotNull MultipartFile image,

        @NotNull MultipartFile video,

        @NotNull @Min(value = 0) Long price,

        @NotNull @Min(value = 0) Integer quantity) {
}
