package com.blimp.backend.controller;

import com.blimp.backend.dto.BlimpResponse;
import com.blimp.backend.dto.CreateProductRequest;
import com.blimp.backend.dto.ProductResponse;
import com.blimp.backend.dto.ProductsResponse;
import com.blimp.backend.dto.UpdateProductRequest;
import com.blimp.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<BlimpResponse<ProductResponse>> createProduct(@RequestBody CreateProductRequest requestBody) {
        ProductResponse createProductResponse = productService.createProduct(requestBody);
        BlimpResponse<ProductResponse> response = new BlimpResponse<>(createProductResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlimpResponse<ProductResponse>> updateProduct(@PathVariable Long id,
            @RequestBody UpdateProductRequest requestBody) {
        ProductResponse uppdateProductResponse = productService.updateProduct(id, requestBody);
        BlimpResponse<ProductResponse> response = new BlimpResponse<>(uppdateProductResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public BlimpResponse<Boolean> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @GetMapping
    public ResponseEntity<BlimpResponse<ProductsResponse>> getAllProducts() {
        BlimpResponse<ProductsResponse> response = productService.getAllProducts();
        return ResponseEntity.ok(response);
    }
}
