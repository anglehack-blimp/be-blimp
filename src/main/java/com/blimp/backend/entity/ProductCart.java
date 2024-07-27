package com.blimp.backend.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class ProductCart {

    @EmbeddedId
    private ProductCartKey productCartKey;

    private Integer quantity;

}
