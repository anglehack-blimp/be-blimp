package com.blimp.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductCartResponse(

        @NotBlank
        String productName,

        @NotNull
        Integer quantity

) { }
