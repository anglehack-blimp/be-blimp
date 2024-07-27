package com.blimp.backend.service;

import com.blimp.backend.dto.BlimpResponse;
import com.blimp.backend.dto.CreateProductRequest;
import com.blimp.backend.dto.ProductResponse;
import com.blimp.backend.dto.ProductsResponse;
import com.blimp.backend.dto.UpdateProductRequest;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest productDto);

    ProductResponse updateProduct(Long id, UpdateProductRequest product);

    void deleteProduct(Long id);

    BlimpResponse<ProductsResponse> getAllProducts();

}
