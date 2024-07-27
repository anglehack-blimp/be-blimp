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

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String image;

    @NotBlank
    private String video;

    @NotNull
    private Long price;

    @NotNull
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    @NotNull
    private User user;

    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;
}
