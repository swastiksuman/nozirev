package com.nozirev_service.nozirev_cxp_cart.controller;

import com.nozirev_service.nozirev_cxp_cart.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

class CartControllerTest {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        CartController cartController = new CartController(new CartService());
        webTestClient = WebTestClient.bindToController(cartController).build();
    }

    // ── GET /api/cart/{userId} ────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/cart/{userId} returns 404 for non-existent cart")
    void getCart_nonExistent_shouldReturn404() {
        webTestClient.get()
                .uri("/api/cart/unknown")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.success").isEqualTo(false);
    }

    @Test
    @DisplayName("GET /api/cart/{userId} returns 200 after item is added")
    void getCart_afterAddingItem_shouldReturn200() {
        webTestClient.post().uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addItemBody("user1", 1, "iPhone 11", 599))
                .exchange();

        webTestClient.get()
                .uri("/api/cart/user1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.cart.userId").isEqualTo("user1");
    }

    // ── POST /api/cart/create ─────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/cart/create returns 201 with empty cart")
    void createCart_shouldReturn201() {
        webTestClient.post()
                .uri("/api/cart/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"userId\":\"user1\"}")
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.cart.userId").isEqualTo("user1")
                .jsonPath("$.cart.totalCount").isEqualTo(0);
    }

    @Test
    @DisplayName("POST /api/cart/create with missing userId returns 400")
    void createCart_missingUserId_shouldReturn400() {
        webTestClient.post()
                .uri("/api/cart/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{}")
                .exchange()
                .expectStatus().isBadRequest();
    }

    // ── POST /api/cart/addItem ────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/cart/addItem returns 201 and adds item correctly")
    void addItem_shouldReturn201AndAddItem() {
        webTestClient.post()
                .uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addItemBody("user1", 1, "iPhone 11", 599))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.cart.totalCount").isEqualTo(1)
                .jsonPath("$.cart.totalAmount").isEqualTo(599.0)
                .jsonPath("$.cart.items[0].productName").isEqualTo("iPhone 11");
    }

    @Test
    @DisplayName("POST /api/cart/addItem same product twice increments quantity")
    void addItem_sameProductTwice_shouldIncrementQuantity() {
        String body = addItemBody("user2", 1, "iPhone 11", 599);
        webTestClient.post().uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(body).exchange();
        webTestClient.post().uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON).bodyValue(body)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.cart.totalCount").isEqualTo(2)
                .jsonPath("$.cart.items[0].quantity").isEqualTo(2)
                .jsonPath("$.cart.totalAmount").isEqualTo(1198.0);
    }

    @Test
    @DisplayName("POST /api/cart/addItem with missing userId returns 400")
    void addItem_missingUserId_shouldReturn400() {
        webTestClient.post()
                .uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"item\":{\"productId\":1,\"productName\":\"iPhone 11\",\"imageUrl\":\"https://example.com\",\"amount\":599,\"quantity\":1}}")
                .exchange()
                .expectStatus().isBadRequest();
    }

    // ── PUT /api/cart/updateItem ──────────────────────────────────────────────

    @Test
    @DisplayName("PUT /api/cart/updateItem updates item quantity")
    void updateItem_shouldUpdateQuantity() {
        webTestClient.post().uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addItemBody("user3", 1, "iPhone 11", 599))
                .exchange();

        webTestClient.put()
                .uri("/api/cart/updateItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"userId\":\"user3\",\"productId\":1,\"quantity\":3}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.cart.items[0].quantity").isEqualTo(3)
                .jsonPath("$.cart.totalCount").isEqualTo(3)
                .jsonPath("$.cart.totalAmount").isEqualTo(1797.0);
    }

    @Test
    @DisplayName("PUT /api/cart/updateItem with quantity 0 removes item")
    void updateItem_quantityZero_shouldRemoveItem() {
        webTestClient.post().uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addItemBody("user4", 1, "iPhone 11", 599))
                .exchange();

        webTestClient.put()
                .uri("/api/cart/updateItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"userId\":\"user4\",\"productId\":1,\"quantity\":0}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.cart.totalCount").isEqualTo(0);
    }

    @Test
    @DisplayName("PUT /api/cart/updateItem for non-existent cart returns 404")
    void updateItem_nonExistentCart_shouldReturn404() {
        webTestClient.put()
                .uri("/api/cart/updateItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"userId\":\"ghost\",\"productId\":1,\"quantity\":2}")
                .exchange()
                .expectStatus().isNotFound();
    }

    // ── DELETE /api/cart/{userId}/item/{productId} ────────────────────────────

    @Test
    @DisplayName("DELETE /api/cart/{userId}/item/{productId} removes that product")
    void removeItem_shouldRemoveItem() {
        webTestClient.post().uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addItemBody("user5", 1, "iPhone 11", 599))
                .exchange();

        webTestClient.delete()
                .uri("/api/cart/user5/item/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.cart.totalCount").isEqualTo(0);
    }

    @Test
    @DisplayName("DELETE /api/cart/{userId}/item/{productId} for non-existent cart returns 404")
    void removeItem_nonExistentCart_shouldReturn404() {
        webTestClient.delete()
                .uri("/api/cart/ghost/item/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    // ── DELETE /api/cart/{userId}/clear ──────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/cart/{userId}/clear clears all items")
    void clearCart_shouldClearAllItems() {
        webTestClient.post().uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addItemBody("user6", 1, "iPhone 11", 599))
                .exchange();
        webTestClient.post().uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addItemBody("user6", 2, "iPad Air", 799))
                .exchange();

        webTestClient.delete()
                .uri("/api/cart/user6/clear")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.cart.totalCount").isEqualTo(0)
                .jsonPath("$.cart.totalAmount").isEqualTo(0.0);
    }

    @Test
    @DisplayName("DELETE /api/cart/{userId}/clear for non-existent cart returns 404")
    void clearCart_nonExistentCart_shouldReturn404() {
        webTestClient.delete()
                .uri("/api/cart/ghost/clear")
                .exchange()
                .expectStatus().isNotFound();
    }

    // ── DELETE /api/cart/{userId} ─────────────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/cart/{userId} deletes cart and returns 200")
    void deleteCart_shouldDeleteCart() {
        webTestClient.post().uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addItemBody("user7", 1, "iPhone 11", 599))
                .exchange();

        webTestClient.delete()
                .uri("/api/cart/user7")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.message").isEqualTo("Cart deleted successfully");
    }

    @Test
    @DisplayName("DELETE /api/cart/{userId} for non-existent cart returns 404")
    void deleteCart_nonExistentCart_shouldReturn404() {
        webTestClient.delete()
                .uri("/api/cart/ghost")
                .exchange()
                .expectStatus().isNotFound();
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private String addItemBody(String userId, int productId, String name, double amount) {
        return """
                {
                  "userId": "%s",
                  "item": {
                    "productId": %d,
                    "productName": "%s",
                    "imageUrl": "https://example.com/img.jpg",
                    "amount": %s,
                    "quantity": 1,
                    "description": "%s"
                  }
                }
                """.formatted(userId, productId, name, amount, name);
    }
}
