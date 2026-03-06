package com.nozirev_service.nozirev_cxp_cart.service;

import com.nozirev_service.nozirev_cxp_cart.model.Cart;
import com.nozirev_service.nozirev_cxp_cart.model.CartItem;
import com.nozirev_service.nozirev_cxp_cart.model.ProfileDetails;
import com.nozirev_service.nozirev_cxp_cart.model.ShippingDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    private CartService cartService;
    private CartItem mockItem;

    @BeforeEach
    void setUp() {
        cartService = new CartService();
        mockItem = new CartItem(1, "iPhone 11", "https://example.com/img.jpg", 599.0, 1, "iPhone 11");
    }

    // ── createCart ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("createCart should return a new cart with cartId and userId")
    void createCart_shouldReturnNewCart() {
        Cart cart = cartService.createCart("user1");
        assertNotNull(cart);
        assertNotNull(cart.getCartId());
        assertEquals("user1", cart.getUserId());
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    @DisplayName("createCart called twice for same user should return same cart")
    void createCart_calledTwice_shouldReturnSameCart() {
        Cart cart1 = cartService.createCart("user1");
        Cart cart2 = cartService.createCart("user1");
        assertEquals(cart1.getCartId(), cart2.getCartId());
    }

    @Test
    @DisplayName("createCart should store provided profile and shipping details")
    void createCart_withDetails_shouldStoreDetails() {
        ProfileDetails profileDetails = new ProfileDetails();
        profileDetails.setFirstName("John");
        profileDetails.setLastName("Doe");

        ShippingDetails shippingDetails = new ShippingDetails();
        shippingDetails.setAddressLine1("12 Main St");
        shippingDetails.setPinCode("560001");

        Cart cart = cartService.createCart("user-details", profileDetails, shippingDetails);

        assertNotNull(cart.getProfileDetails());
        assertNotNull(cart.getShippingDetails());
        assertEquals("John", cart.getProfileDetails().getFirstName());
        assertEquals("12 Main St", cart.getShippingDetails().getAddressLine1());
    }

    // ── getCart ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getCart should return empty optional for non-existent user")
    void getCart_nonExistentUser_shouldReturnEmpty() {
        assertTrue(cartService.getCart("unknown").isEmpty());
    }

    @Test
    @DisplayName("getCart should return cart after creation")
    void getCart_afterCreation_shouldReturnCart() {
        cartService.createCart("user1");
        Optional<Cart> cart = cartService.getCart("user1");
        assertTrue(cart.isPresent());
        assertEquals("user1", cart.get().getUserId());
    }

    // ── addItem ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("addItem should add new item and update totals")
    void addItem_newItem_shouldUpdateTotals() {
        Cart cart = cartService.addItem("user1", mockItem);
        assertEquals(1, cart.getItems().size());
        assertEquals(1, cart.getTotalCount());
        assertEquals(599.0, cart.getTotalAmount());
    }

    @Test
    @DisplayName("addItem with same productId should increment quantity")
    void addItem_existingProduct_shouldIncrementQuantity() {
        cartService.addItem("user1", mockItem);
        Cart cart = cartService.addItem("user1", mockItem);
        assertEquals(1, cart.getItems().size());
        assertEquals(2, cart.getItems().get(0).getQuantity());
        assertEquals(2, cart.getTotalCount());
        assertEquals(1198.0, cart.getTotalAmount());
    }

    @Test
    @DisplayName("addItem with different products should add both")
    void addItem_differentProducts_shouldAddBothItems() {
        CartItem item2 = new CartItem(2, "iPad Air", "https://example.com/img2.jpg", 599.0, 1, "iPad Air");
        cartService.addItem("user1", mockItem);
        Cart cart = cartService.addItem("user1", item2);
        assertEquals(2, cart.getItems().size());
        assertEquals(2, cart.getTotalCount());
        assertEquals(1198.0, cart.getTotalAmount());
    }

    @Test
    @DisplayName("addItem should create cart if not exists")
    void addItem_noExistingCart_shouldCreateCartAndAddItem() {
        Cart cart = cartService.addItem("newuser", mockItem);
        assertNotNull(cart);
        assertEquals(1, cart.getItems().size());
    }

    @Test
    @DisplayName("addItem should store provided profile and shipping details")
    void addItem_withDetails_shouldStoreDetails() {
        ProfileDetails profileDetails = new ProfileDetails();
        profileDetails.setMobileNumber("9999999999");

        ShippingDetails shippingDetails = new ShippingDetails();
        shippingDetails.setDeliveryType("Express");

        Cart cart = cartService.addItem("user-add-details", mockItem, profileDetails, shippingDetails);

        assertEquals(1, cart.getItems().size());
        assertEquals("9999999999", cart.getProfileDetails().getMobileNumber());
        assertEquals("Express", cart.getShippingDetails().getDeliveryType());
    }

    // ── updateItem ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("updateItem should update quantity of existing item")
    void updateItem_existingItem_shouldUpdateQuantity() {
        cartService.addItem("user1", mockItem);
        Optional<Cart> result = cartService.updateItem("user1", 1, 5);
        assertTrue(result.isPresent());
        assertEquals(5, result.get().getItems().get(0).getQuantity());
        assertEquals(5, result.get().getTotalCount());
        assertEquals(2995.0, result.get().getTotalAmount());
    }

    @Test
    @DisplayName("updateItem with quantity 0 should remove item")
    void updateItem_quantityZero_shouldRemoveItem() {
        cartService.addItem("user1", mockItem);
        Optional<Cart> result = cartService.updateItem("user1", 1, 0);
        assertTrue(result.isPresent());
        assertTrue(result.get().getItems().isEmpty());
        assertEquals(0, result.get().getTotalCount());
        assertEquals(0.0, result.get().getTotalAmount());
    }

    @Test
    @DisplayName("updateItem for non-existent user should return empty")
    void updateItem_nonExistentUser_shouldReturnEmpty() {
        assertTrue(cartService.updateItem("ghost", 1, 3).isEmpty());
    }

    // ── removeItem ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("removeItem should remove item and recalculate totals")
    void removeItem_existingItem_shouldRemoveAndRecalculate() {
        cartService.addItem("user1", mockItem);
        Optional<Cart> result = cartService.removeItem("user1", 1);
        assertTrue(result.isPresent());
        assertTrue(result.get().getItems().isEmpty());
        assertEquals(0, result.get().getTotalCount());
    }

    @Test
    @DisplayName("removeItem for non-existent user should return empty")
    void removeItem_nonExistentUser_shouldReturnEmpty() {
        assertTrue(cartService.removeItem("ghost", 1).isEmpty());
    }

    // ── clearCart ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("clearCart should remove all items and reset totals")
    void clearCart_shouldRemoveAllItems() {
        CartItem item2 = new CartItem(2, "iPad", "https://example.com", 799.0, 1, "iPad");
        cartService.addItem("user1", mockItem);
        cartService.addItem("user1", item2);
        Optional<Cart> result = cartService.clearCart("user1");
        assertTrue(result.isPresent());
        assertTrue(result.get().getItems().isEmpty());
        assertEquals(0, result.get().getTotalCount());
        assertEquals(0.0, result.get().getTotalAmount());
    }

    @Test
    @DisplayName("clearCart for non-existent user should return empty")
    void clearCart_nonExistentUser_shouldReturnEmpty() {
        assertTrue(cartService.clearCart("ghost").isEmpty());
    }

    // ── deleteCart ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("deleteCart should return true and remove cart")
    void deleteCart_existingCart_shouldReturnTrue() {
        cartService.createCart("user1");
        assertTrue(cartService.deleteCart("user1"));
        assertTrue(cartService.getCart("user1").isEmpty());
    }

    @Test
    @DisplayName("deleteCart should return false for non-existent cart")
    void deleteCart_nonExistentCart_shouldReturnFalse() {
        assertFalse(cartService.deleteCart("ghost"));
    }
}
