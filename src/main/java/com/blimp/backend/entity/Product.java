package com.blimp.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Product name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description; // TEXT field in database

    @NotBlank(message = "Product image is required")
    @Size(max = 255, message = "Product image URL must not exceed 255 characters")
    private String image;

    @NotBlank(message = "Product video is required")
    @Size(max = 255, message = "Product video URL must not exceed 255 characters")
    private String video;

    @NotNull(message = "Product price is required")
    @Min(value = 0, message = "Product price must be a positive value")
    private Long price;

    @NotNull(message = "Product quantity is required")
    @Min(value = 0, message = "Product quantity must be a non-negative value")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    @NotNull(message = "Product must be associated with a user")
    private User user;

    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;
}
