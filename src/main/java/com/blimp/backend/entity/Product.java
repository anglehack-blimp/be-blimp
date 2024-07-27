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

    private String name;

    private String description;

    private String image;

    private String video;

    private Long price;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @OneToMany(mappedBy = "productCartKey.product")
    private List<ProductCart> productCarts;
}
