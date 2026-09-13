package com.nozirev_service.nozirev_cxp_cart.service;

import com.nozirev_service.nozirev_cxp_cart.model.Cart;
import com.nozirev_service.nozirev_cxp_cart.model.CartItem;
import com.nozirev_service.nozirev_cxp_cart.model.ProfileDetails;
import com.nozirev_service.nozirev_cxp_cart.model.ShippingDetails;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
        return createCart(userId, null, null);
    }

    @CachePut(value = "carts", key = "#userId")
    public Cart createCart(String userId, ProfileDetails profileDetails, ShippingDetails shippingDetails) {
        Cart cart = cartStore.computeIfAbsent(userId, mappedUserId ->
                new Cart(UUID.randomUUID().toString(), mappedUserId));
        applyDetails(cart, profileDetails, shippingDetails);
        return cart;
    }

    @Cacheable(value = "carts", key = "#userId", unless = "#result == null")
    public Optional<Cart> getCart(String userId) {
        return Optional.ofNullable(cartStore.get(userId));
    }

    public Cart addItem(String userId, CartItem item) {
        return addItem(userId, item, null, null);
    }

    @CachePut(value = "carts", key = "#userId")
    public Cart addItem(String userId, CartItem item, ProfileDetails profileDetails, ShippingDetails shippingDetails) {
        Cart cart = createCart(userId, profileDetails, shippingDetails);

        Optional<CartItem> existing = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProductId().equals(item.getProductId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + item.getQuantity());
        } else {
            cart.getItems().add(item);
        }

        cart.recalculateTotals();
        return cart;
    }

    @CachePut(value = "carts", key = "#userId", unless = "#result == null")
    public Optional<Cart> updateItem(String userId, Integer productId, int quantity) {
        return getCart(userId).map(cart -> {
            if (quantity == 0) {
                cart.getItems().removeIf(cartItem -> cartItem.getProductId().equals(productId));
            } else {
                cart.getItems().stream()
                        .filter(cartItem -> cartItem.getProductId().equals(productId))
                        .findFirst()
                        .ifPresent(cartItem -> cartItem.setQuantity(quantity));
            }
            cart.recalculateTotals();
            return cart;
        });
    }

    @CachePut(value = "carts", key = "#userId", unless = "#result == null")
    public Optional<Cart> removeItem(String userId, Integer productId) {
        return getCart(userId).map(cart -> {
            cart.getItems().removeIf(cartItem -> cartItem.getProductId().equals(productId));
            cart.recalculateTotals();
            return cart;
        });
    }

    @CachePut(value = "carts", key = "#userId", unless = "#result == null")
    public Optional<Cart> clearCart(String userId) {
        return getCart(userId).map(cart -> {
            cart.getItems().clear();
            cart.recalculateTotals();
            return cart;
        });
    }

    @CacheEvict(value = "carts", key = "#userId")
    public boolean deleteCart(String userId) {
        return cartStore.remove(userId) != null;
    }

    private void applyDetails(Cart cart, ProfileDetails profileDetails, ShippingDetails shippingDetails) {
        if (profileDetails != null) {
            cart.setProfileDetails(profileDetails);
        }
        if (shippingDetails != null) {
            cart.setShippingDetails(shippingDetails);
        }
    }
}
