package com.blimp.backend.service.impl;

import com.blimp.backend.dto.CartRequest;
import com.blimp.backend.dto.CartResponse;
import com.blimp.backend.dto.ProductCartResponse;
import com.blimp.backend.entity.*;
import com.blimp.backend.repository.CartRepository;
import com.blimp.backend.repository.ProductCartRepository;
import com.blimp.backend.repository.ProductRepository;
import com.blimp.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final ProductCartRepository productCartRepository;

    @Override
    @Transactional
    public CartResponse addToCart(CartRequest request, User user) {
        var cart = new Cart();
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUser(user);
        cart.setStatus(CartStatus.PENDING);
        cartRepository.save(cart);

        var productCarts = new ArrayList<ProductCart>();
        for (var productCartRequest : request.cartList()) {
            var product = productRepository.findFirstByName(productCartRequest.productName()).orElseThrow();
            product.setQuantity(product.getQuantity() - productCartRequest.quantity());

            var productCartKey = new ProductCartKey();
            productCartKey.setProduct(productRepository.save(product));
            productCartKey.setCart(cart);

            var productCart = new ProductCart();
            productCart.setProductCartKey(productCartKey);
            productCart.setQuantity(productCartRequest.quantity());

            productCarts.add(productCart);
        }

        productCartRepository.saveAll(productCarts);

        return new CartResponse(
                productCarts.stream().map(productCart ->
                        new ProductCartResponse(
                                productCart.getProductCartKey().getProduct().getName(),
                                productCart.getQuantity()))
                        .toList()
        );
    }
}
