package com.blimp.backend.service;

import com.blimp.backend.dto.BlimpResponse;
import com.blimp.backend.dto.CreateProductRequest;
import com.blimp.backend.dto.ProductResponse;
import com.blimp.backend.dto.ProductsResponse;
import com.blimp.backend.dto.UpdateProductRequest;
import com.blimp.backend.entity.User;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request, User user);

    ProductResponse updateProduct(Long id, UpdateProductRequest product);

    BlimpResponse<Boolean> deleteProduct(Long id);

    BlimpResponse<ProductsResponse> getAllProducts();

}
