package com.nozirev_service.nozirev_cxp_cart.service;

import com.nozirev_service.nozirev_cxp_cart.model.Cart;
import com.nozirev_service.nozirev_cxp_cart.model.CartItem;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartService {

    // In-memory store: userId -> Cart
    private final Map<String, Cart> cartStore = new ConcurrentHashMap<>();

    public Cart createCart(String userId) {
        return cartStore.computeIfAbsent(userId, id ->
                new Cart(UUID.randomUUID().toString(), id));
    }

    public Optional<Cart> getCart(String userId) {
        return Optional.ofNullable(cartStore.get(userId));
    }

    public Cart addItem(String userId, CartItem item) {
        Cart cart = createCart(userId);

        Optional<CartItem> existing = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(item.getProductId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + item.getQuantity());
        } else {
            cart.getItems().add(item);
        }

        cart.recalculateTotals();
        return cart;
    }

    public Optional<Cart> updateItem(String userId, Integer productId, int quantity) {
        return getCart(userId).map(cart -> {
            if (quantity == 0) {
                cart.getItems().removeIf(i -> i.getProductId().equals(productId));
            } else {
                cart.getItems().stream()
                        .filter(i -> i.getProductId().equals(productId))
                        .findFirst()
                        .ifPresent(i -> i.setQuantity(quantity));
            }
            cart.recalculateTotals();
            return cart;
        });
    }

    public Optional<Cart> removeItem(String userId, Integer productId) {
        return getCart(userId).map(cart -> {
            cart.getItems().removeIf(i -> i.getProductId().equals(productId));
            cart.recalculateTotals();
            return cart;
        });
    }

    public Optional<Cart> clearCart(String userId) {
        return getCart(userId).map(cart -> {
            cart.getItems().clear();
            cart.recalculateTotals();
            return cart;
        });
    }

    public boolean deleteCart(String userId) {
        return cartStore.remove(userId) != null;
    }
}
