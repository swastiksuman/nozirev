package com.nozirev_service.nozirev_cxp_cart.service;

import com.nozirev_service.nozirev_cxp_cart.model.Cart;
import com.nozirev_service.nozirev_cxp_cart.model.CartItem;
import com.nozirev_service.nozirev_cxp_cart.model.ProfileDetails;
import com.nozirev_service.nozirev_cxp_cart.model.ShippingDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CacheIntegrationTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CacheManager cacheManager;

    private CartItem mockItem;

    @BeforeEach
    void setUp() {
        mockItem = new CartItem(1, "iPhone 11", "https://example.com/img.jpg", 599.0, 1, "iPhone 11");
        // Clear cache before each test
        var cartsCache = cacheManager.getCache("carts");
        if (cartsCache != null) {
            cartsCache.clear();
        }
    }

    @Test
    @DisplayName("createCart should cache cart in Caffeine")
    void createCart_shouldCacheInCaffeine() {
        Cart cart = cartService.createCart("cache-user-1");

        assertNotNull(cart);
        var cartsCache = cacheManager.getCache("carts");
        assertNotNull(cartsCache);
        var cachedValue = cartsCache.get("cache-user-1", Cart.class);
        assertNotNull(cachedValue);
        assertEquals(cart.getCartId(), cachedValue.getCartId());
    }

    @Test
    @DisplayName("getCart should retrieve from cache after first fetch")
    void getCart_shouldRetrieveFromCache() {
        cartService.createCart("cache-user-2");

        // First call fetches from store and caches
        var cart1 = cartService.getCart("cache-user-2");
        assertTrue(cart1.isPresent());

        // Verify it's in cache
        var cartsCache = cacheManager.getCache("carts");
        assertNotNull(cartsCache);
        var cachedValue = cartsCache.get("cache-user-2");
        assertNotNull(cachedValue);
    }

    @Test
    @DisplayName("addItem should update cache")
    void addItem_shouldUpdateCache() {
        cartService.createCart("cache-user-3");
        cartService.addItem("cache-user-3", mockItem);

        // Verify cache contains updated cart with item
        var cartsCache = cacheManager.getCache("carts");
        assertNotNull(cartsCache);
        var cachedCart = cartsCache.get("cache-user-3", Cart.class);
        assertNotNull(cachedCart);
        assertEquals(1, cachedCart.getItems().size());
        assertEquals(1, cachedCart.getTotalCount());
    }

    @Test
    @DisplayName("addItem with profile and shipping details should cache complete cart")
    void addItem_withDetails_shouldCacheCompleteCart() {
        ProfileDetails profileDetails = new ProfileDetails();
        profileDetails.setFirstName("John");
        profileDetails.setEmail("john@example.com");

        ShippingDetails shippingDetails = new ShippingDetails();
        shippingDetails.setAddressLine1("123 Main St");
        shippingDetails.setDeliveryType("Express");

        cartService.addItem("cache-user-4", mockItem, profileDetails, shippingDetails);

        // Verify cache contains complete cart with all details
        var cartsCache = cacheManager.getCache("carts");
        assertNotNull(cartsCache);
        var cachedCart = cartsCache.get("cache-user-4", Cart.class);
        assertNotNull(cachedCart);
        assertEquals("John", cachedCart.getProfileDetails().getFirstName());
        assertEquals("john@example.com", cachedCart.getProfileDetails().getEmail());
        assertEquals("123 Main St", cachedCart.getShippingDetails().getAddressLine1());
        assertEquals("Express", cachedCart.getShippingDetails().getDeliveryType());
    }

    @Test
    @DisplayName("updateItem should update cache")
    void updateItem_shouldUpdateCache() {
        cartService.createCart("cache-user-5");
        cartService.addItem("cache-user-5", mockItem);
        cartService.updateItem("cache-user-5", 1, 5);

        // Verify cache contains updated item quantity
        var cartsCache = cacheManager.getCache("carts");
        assertNotNull(cartsCache);
        var cachedCart = cartsCache.get("cache-user-5", Cart.class);
        assertNotNull(cachedCart);
        assertEquals(5, cachedCart.getItems().getFirst().getQuantity());
        assertEquals(5, cachedCart.getTotalCount());
    }

    @Test
    @DisplayName("removeItem should update cache")
    void removeItem_shouldUpdateCache() {
        cartService.createCart("cache-user-6");
        cartService.addItem("cache-user-6", mockItem);
        cartService.removeItem("cache-user-6", 1);

        // Verify cache shows item removed
        var cartsCache = cacheManager.getCache("carts");
        assertNotNull(cartsCache);
        var cachedCart = cartsCache.get("cache-user-6", Cart.class);
        assertNotNull(cachedCart);
        assertTrue(cachedCart.getItems().isEmpty());
    }

    @Test
    @DisplayName("clearCart should update cache")
    void clearCart_shouldUpdateCache() {
        cartService.createCart("cache-user-7");
        cartService.addItem("cache-user-7", mockItem);
        cartService.clearCart("cache-user-7");

        // Verify cache shows cleared cart
        var cartsCache = cacheManager.getCache("carts");
        assertNotNull(cartsCache);
        var cachedCart = cartsCache.get("cache-user-7", Cart.class);
        assertNotNull(cachedCart);
        assertTrue(cachedCart.getItems().isEmpty());
    }

    @Test
    @DisplayName("deleteCart should evict from cache")
    void deleteCart_shouldEvictFromCache() {
        cartService.createCart("cache-user-8");
        cartService.deleteCart("cache-user-8");

        // Verify cart is removed from cache
        var cartsCache = cacheManager.getCache("carts");
        assertNotNull(cartsCache);
        var cachedValue = cartsCache.get("cache-user-8");
        assertNull(cachedValue);
    }
}


