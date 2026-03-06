package com.nozirev_service.nozirev_cxp_cart.controller;

import com.nozirev_service.nozirev_cxp_cart.model.CartRequest;
import com.nozirev_service.nozirev_cxp_cart.model.CartResponse;
import com.nozirev_service.nozirev_cxp_cart.model.UpdateCartRequest;
import com.nozirev_service.nozirev_cxp_cart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // GET /api/cart/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable String userId) {
        return cartService.getCart(userId)
                .map(cart -> ResponseEntity.ok(CartResponse.ok("Cart retrieved successfully", cart)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(CartResponse.error("Cart not found for user: " + userId)));
    }

    // POST /api/cart/create
    @PostMapping("/create")
    public ResponseEntity<CartResponse> createCart(@RequestBody @Valid CartRequest request) {
        var cart = cartService.createCart(
                request.getUserId(),
                request.getProfileDetails(),
                request.getShippingDetails());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CartResponse.ok("Cart created successfully", cart));
    }

    // POST /api/cart/addItem
    @PostMapping("/addItem")
    public ResponseEntity<CartResponse> addItem(@RequestBody @Valid CartRequest request) {
        var cart = cartService.addItem(
                request.getUserId(),
                request.getItem(),
                request.getProfileDetails(),
                request.getShippingDetails());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CartResponse.ok("Item added to cart", cart));
    }

    // PUT /api/cart/updateItem
    @PutMapping("/updateItem")
    public ResponseEntity<CartResponse> updateItem(@RequestBody @Valid UpdateCartRequest request) {
        return cartService.updateItem(request.getUserId(), request.getProductId(), request.getQuantity())
                .map(cart -> ResponseEntity.ok(CartResponse.ok("Cart updated successfully", cart)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(CartResponse.error("Cart not found for user: " + request.getUserId())));
    }

    // DELETE /api/cart/{userId}/item/{productId}
    @DeleteMapping("/{userId}/item/{productId}")
    public ResponseEntity<CartResponse> removeItem(
            @PathVariable String userId,
            @PathVariable Integer productId) {
        return cartService.removeItem(userId, productId)
                .map(cart -> ResponseEntity.ok(CartResponse.ok("Item removed from cart", cart)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(CartResponse.error("Cart not found for user: " + userId)));
    }

    // DELETE /api/cart/{userId}/clear
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<CartResponse> clearCart(@PathVariable String userId) {
        return cartService.clearCart(userId)
                .map(cart -> ResponseEntity.ok(CartResponse.ok("Cart cleared successfully", cart)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(CartResponse.error("Cart not found for user: " + userId)));
    }

    // DELETE /api/cart/{userId}
    @DeleteMapping("/{userId}")
    public ResponseEntity<CartResponse> deleteCart(@PathVariable String userId) {
        boolean deleted = cartService.deleteCart(userId);
        if (deleted) {
            return ResponseEntity.ok(CartResponse.ok("Cart deleted successfully", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CartResponse.error("Cart not found for user: " + userId));
    }
}
