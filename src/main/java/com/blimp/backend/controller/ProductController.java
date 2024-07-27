package com.blimp.backend.controller;

import com.blimp.backend.dto.BlimpResponse;
import com.blimp.backend.dto.CreateProductRequest;
import com.blimp.backend.dto.ProductResponse;
import com.blimp.backend.dto.ProductsResponse;
import com.blimp.backend.dto.UpdateProductRequest;
import com.blimp.backend.entity.User;
import com.blimp.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BlimpResponse<ProductResponse>> createProduct(@ModelAttribute CreateProductRequest requestBody, User user) {
        var createProductResponse = productService.createProduct(requestBody, user);
        var response = new BlimpResponse<>(createProductResponse);
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
