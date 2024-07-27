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
import com.blimp.backend.repository.UserRepository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

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
        Path filePath = Paths.get("directory/archive/", filename);

        // memastikan input file berhasil
        try {
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
    public ProductResponse createProduct(CreateProductRequest product) {

        // mengambil user sesuai dengan username
        var row = new Product();
        Optional<User> userOptional = userRepository.findByUsername("irvan");
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found with username: irvan"));
        row.setUser(user);

        // mengisi informasi lainnya
        row.setName(product.name());
        row.setDescription(product.description());
        row.setPrice(product.price());
        row.setQuantity(product.quantity());

        // mengisi gambar dan video dengan nama dari file yang diupload
        row.setImage(getUploadedFilename(product.image(), "image"));
        row.setVideo(getUploadedFilename(product.video(), "video"));

        // menyimpan ke database
        productRepository.save(row);

        // menyiapkan response product
        return new ProductResponse(
                row.getId(),
                row.getName(),
                row.getDescription(),
                row.getImage(),
                row.getVideo(),
                row.getPrice(),
                row.getQuantity());
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
        try {
            productRepository.deleteById(id);
            return new BlimpResponse<>(true);
        } catch (EmptyResultDataAccessException e) {
            return new BlimpResponse<>(false, "Product not found with ID: " + id);
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
