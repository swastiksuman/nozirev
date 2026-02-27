package com.nozirev_service.nozirev_soe;

import com.nozirev_service.nozirev_soe.config.CorsConfig;
import com.nozirev_service.nozirev_soe.controller.ProductController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = ProductController.class)
@Import(CorsConfig.class)
class ProductControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Integration: GET /api/getProductList should return 7 products")
    void getProductList_integration_shouldReturnSevenProducts() {
        webTestClient.get()
                .uri("/api/getProductList")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(7)
                .jsonPath("$[0].productName").isEqualTo("iPhone 11")
                .jsonPath("$[6].productName").isEqualTo("iPhone 17");
    }

    @Test
    @DisplayName("Integration: CORS headers should be present for allowed origin")
    void cors_shouldAllowConfiguredOrigin() {
        webTestClient.get()
                .uri("/api/getProductList")
                .header("Origin", "http://localhost:3000")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Access-Control-Allow-Origin", "http://localhost:3000")
                .expectHeader().valueEquals("Access-Control-Allow-Credentials", "true");
    }

    @Test
    @DisplayName("Integration: CORS preflight request should succeed for allowed origin")
    void cors_preflightRequest_shouldSucceed() {
        webTestClient.options()
                .uri("/api/getProductList")
                .header("Origin", "http://localhost:3000")
                .header("Access-Control-Request-Method", "GET")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Access-Control-Allow-Origin", "http://localhost:3000")
                .expectHeader().exists("Access-Control-Allow-Methods");
    }

    @Test
    @DisplayName("Integration: CORS should reject disallowed origin")
    void cors_shouldRejectDisallowedOrigin() {
        webTestClient.get()
                .uri("/api/getProductList")
                .header("Origin", "http://malicious-site.com")
                .exchange()
                .expectHeader().doesNotExist("Access-Control-Allow-Origin");
    }

    @Test
    @DisplayName("Integration: GET /api/unknown should return 404")
    void unknownEndpoint_shouldReturn404() {
        webTestClient.get()
                .uri("/api/unknown")
                .exchange()
                .expectStatus().isNotFound();
    }
}



