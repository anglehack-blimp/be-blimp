package com.blimp.backend.repository;

import com.blimp.backend.entity.ProductCart;
import com.blimp.backend.entity.ProductCartKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, ProductCartKey> { }
