package com.blimp.backend.service.impl;

import com.blimp.backend.dto.BlimpResponse;
import com.blimp.backend.dto.CreateProductRequest;
import com.blimp.backend.dto.ProductResponse;
import com.blimp.backend.dto.ProductsResponse;
import com.blimp.backend.dto.UpdateProductRequest;
import com.blimp.backend.entity.Product;
import com.blimp.backend.entity.User;
import com.blimp.backend.service.ProductService;

import lombok.RequiredArgsConstructor;

import com.blimp.backend.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RequiredArgsConstructor
public class ProductServiceImplOld implements ProductService {

    private final ProductRepository productRepository;

    // @return mengembalikan filename
    private String getUploadedFilename(MultipartFile file, String expectedType) {

        // memerisksa ketersediaan file
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(expectedType + " file is required.");
        }

        // memastikan file path sudah sesuai
        if (!isValidFileType(file, expectedType)) {
            throw new IllegalArgumentException("Invalid file type. Please upload a valid " + expectedType + " file.");
        }

        // menyimpan file ke server directory
        String filename = generateUniqueFilename(file.getOriginalFilename());
        Path directoryPath = Paths.get("documents/archive/");
        Path filePath = directoryPath.resolve(filename);

        // memastikan input file berhasil
        // memastikan directory sudah tersedia
        try {
            Files.createDirectories(directoryPath);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error saving " + expectedType + " file: " + e.getMessage(), e);
        }

        return filename;
    }

    private String generateUniqueFilename(String originalFilename) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String extension = getFileExtension(originalFilename);
        return timestamp + "-" + originalFilename.replace(extension, "") + extension;
    }

    private String getFileExtension(String filename) {
        int lastIndexOfDot = filename.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return ""; // No extension
        }
        return filename.substring(lastIndexOfDot);
    }

    private boolean isValidFileType(MultipartFile file, String expectedType) {
        String contentType = file.getContentType();
        return switch (expectedType) {
            case "image" -> contentType.startsWith("image/");
            case "video" -> contentType.startsWith("video/");
            default -> false;
        };
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request, User user) {

        // mengambil user sesuai dengan username
        var product = new Product();
        product.setUser(user);

        // mengisi informasi lainnya
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());

        // mengisi gambar dan video dengan nama dari file yang diupload
        product.setImage(getUploadedFilename(request.image(), "image"));
        product.setVideo(getUploadedFilename(request.video(), "video"));

        // menyimpan ke database
        productRepository.save(product);

        // menyiapkan response product
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                product.getVideo(),
                product.getPrice(),
                product.getQuantity());
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
        row.setImage(getUploadedFilename(request.image(), "image"));
        row.setVideo(getUploadedFilename(request.video(), "video"));

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
