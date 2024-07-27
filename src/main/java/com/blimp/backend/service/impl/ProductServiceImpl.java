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
import java.util.List;
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
    public ProductResponse updateProduct(Long id, UpdateProductRequest product) {
        return null;
    }

    @Override
    public BlimpResponse<Boolean> deleteProduct(Long id) {
        return null;
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
