package com.blimp.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(

                @NotBlank @Size(max = 20) String username,

                @NotBlank @Size(min = 8, max = 50) String password,

                @Pattern(regexp = "BUYER|SELLER", message = "only 'BUYER' and 'SELLER' are allowed.") String role

) {
}
