package com.blimp.backend.service.impl;

import com.blimp.backend.dto.BlimpResponse;
import com.blimp.backend.dto.CreateProductRequest;
import com.blimp.backend.dto.ProductResponse;
import com.blimp.backend.dto.ProductsResponse;
import com.blimp.backend.dto.UpdateProductRequest;
import com.blimp.backend.entity.Product;
import com.blimp.backend.service.ProductService;

import lombok.RequiredArgsConstructor;

import com.blimp.backend.repository.ProductRepository;
import com.blimp.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ProductResponse createProduct(CreateProductRequest product) {
        var row = new Product();
        var user = userRepository.findByUsername("irvan");

        if (user == null) {
            throw new RuntimeException("User not found with username: irvan");
        }

        row.setUser(user);
        row.setName(product.name());
        row.setDescription(product.description());
        row.setImage(product.image());
        row.setVideo(product.video());
        row.setPrice(product.price());
        row.setQuantity(product.quantity());

        productRepository.save(row);

        var productResponse = new ProductResponse(
                row.getId(),
                row.getName(),
                row.getDescription(),
                row.getImage(),
                row.getVideo(),
                row.getPrice(),
                row.getQuantity());

        return productResponse;
    }

    @Override
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setImage(request.image());
        product.setVideo(request.video());
        product.setQuantity(request.quantity());

        Product updatedProduct = productRepository.save(product);

        return new ProductResponse(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getImage(),
                updatedProduct.getVideo(),
                updatedProduct.getPrice(),
                updatedProduct.getQuantity());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public BlimpResponse<ProductsResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponses = products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getImage(),
                        product.getVideo(),
                        product.getPrice(),
                        product.getQuantity()))
                .toList();

        return new BlimpResponse<>(new ProductsResponse(productResponses));
    }
}
