package com.blimp.backend.service.impl;

import com.blimp.backend.dto.*;
import com.blimp.backend.entity.Product;
import com.blimp.backend.entity.User;
import com.blimp.backend.repository.ProductRepository;
import com.blimp.backend.service.ProductService;
import com.blimp.backend.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ValidationService validationService;


    @Override
    public ProductResponse createProduct(CreateProductRequest request, User user) {
        validationService.validate(request);

        var product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setUser(user);
        product.setQuantity(request.quantity());
        product.setImage(documentSave(".", request.image()));
        product.setVideo(documentSave(".", request.video()));

        productRepository.save(product);

        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getImage(),
                product.getVideo(), product.getPrice(), product.getQuantity());
    }

    @SneakyThrows
    private String documentSave(String filePath, MultipartFile file) {

        var uuid = UUID.randomUUID();
        var path = Path.of(filePath);
        var fullPath = path.resolve(uuid + "." + file.getOriginalFilename().split("\\.")[1]);

        if (!Files.exists(fullPath))
            Files.createDirectories(path);

        file.transferTo(fullPath);
        return fullPath.toString();
    }

    @Override
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
        Product row = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // menyiapkan data product
        row.setName(request.name());
        row.setDescription(request.description());
        row.setQuantity(request.quantity());

        // mengisi gambar dan video dengan nama dari file yang diupload
        row.setImage(documentSave(".", request.image()));
        row.setVideo(documentSave(".", request.video()));

        // menyimpan ke database
        Product updatedProduct = productRepository.save(row);

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
    public BlimpResponse<Boolean> deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return new BlimpResponse<>(false, "Product not found with ID: " + id);
        }
        try {
            productRepository.deleteById(id);
            return new BlimpResponse<>(true, "Product deleted successfully");
        } catch (Exception e) {
            return new BlimpResponse<>(false, "Error deleting product: " + e.getMessage());
        }
    }

    @Override
    public BlimpResponse<ProductsResponse> getAllProducts() {
        var products = productRepository.findAll();

        var productResponses = products.stream()
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
