package com.nozirev_service.nozirev_cxp_cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class CartControllerIntegrationTest {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(@Autowired ApplicationContext context) {
        this.webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    @DisplayName("Integration: Full lifecycle - add, update, remove, clear")
    void fullCartLifecycle() {
        // Add item
        webTestClient.post().uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {"userId":"integUser","item":{"productId":1,"productName":"iPhone 11",
                        "imageUrl":"https://example.com","amount":599,"quantity":1,"description":"Test"}}
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.cart.totalCount").isEqualTo(1)
                .jsonPath("$.cart.totalAmount").isEqualTo(599.0);

        // Update quantity to 3
        webTestClient.put().uri("/api/cart/updateItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"userId\":\"integUser\",\"productId\":1,\"quantity\":3}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.cart.totalCount").isEqualTo(3)
                .jsonPath("$.cart.totalAmount").isEqualTo(1797.0);

        // Remove the item
        webTestClient.delete().uri("/api/cart/integUser/item/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.cart.totalCount").isEqualTo(0);

        // Delete the cart
        webTestClient.delete().uri("/api/cart/integUser")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true);
    }

    @Test
    @DisplayName("Integration: CORS allows configured origin")
    void cors_shouldAllowConfiguredOrigin() {
        webTestClient.post().uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"userId\":\"corsUser\",\"item\":{\"productId\":1,\"productName\":\"iPhone\",\"imageUrl\":\"https://example.com\",\"amount\":599,\"quantity\":1,\"description\":\"Test\"}}")
                .header("Origin", "http://localhost:3000")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().valueEquals("Access-Control-Allow-Origin", "http://localhost:3000")
                .expectHeader().valueEquals("Access-Control-Allow-Credentials", "true");
    }

    @Test
    @DisplayName("Integration: CORS rejects disallowed origin")
    void cors_shouldRejectDisallowedOrigin() {
        webTestClient.post().uri("/api/cart/addItem")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"userId\":\"corsUser2\",\"item\":{\"productId\":1,\"productName\":\"iPhone\",\"imageUrl\":\"https://example.com\",\"amount\":599,\"quantity\":1,\"description\":\"Test\"}}")
                .header("Origin", "http://malicious-site.com")
                .exchange()
                .expectHeader().doesNotExist("Access-Control-Allow-Origin");
    }

    @Test
    @DisplayName("Integration: GET unknown endpoint returns 404")
    void unknownEndpoint_shouldReturn404() {
        webTestClient.get().uri("/api/unknown")
                .exchange()
                .expectStatus().isNotFound();
    }
}
