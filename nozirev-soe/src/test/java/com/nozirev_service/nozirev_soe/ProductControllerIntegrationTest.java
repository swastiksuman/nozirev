package com.nozirev_service.nozirev_soe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class ProductControllerIntegrationTest {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(@Autowired ApplicationContext context) {
        this.webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    @DisplayName("Integration: POST /api/getProductList with smartphones should return 7 products")
    void getProductList_integration_shouldReturnSevenProducts() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"smartphones\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(7)
                .jsonPath("$[0].productName").isEqualTo("iPhone 11")
                .jsonPath("$[6].productName").isEqualTo("iPhone 17");
    }

    @Test
    @DisplayName("Integration: POST /api/getProductList with tablets should return 7 products")
    void getProductList_withTablets_integration_shouldReturnSevenProducts() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"tablets\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(7)
                .jsonPath("$[0].productName").isEqualTo("iPad Air");
    }

    @Test
    @DisplayName("Integration: POST /api/getProductList with watches should return 7 products")
    void getProductList_withWatches_integration_shouldReturnSevenProducts() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"watches\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(7)
                .jsonPath("$[0].productName").isEqualTo("Apple Watch Series 9");
    }

    @Test
    @DisplayName("Integration: POST /api/getProductList with routers should return 7 products")
    void getProductList_withRouters_integration_shouldReturnSevenProducts() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"routers\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(7)
                .jsonPath("$[0].productName").isEqualTo("TP-Link Archer AX73");
    }

    @Test
    @DisplayName("Integration: CORS headers should be present for allowed origin")
    void cors_shouldAllowConfiguredOrigin() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"smartphones\"}")
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
                .header("Access-Control-Request-Method", "POST")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Access-Control-Allow-Origin", "http://localhost:3000")
                .expectHeader().exists("Access-Control-Allow-Methods");
    }

    @Test
    @DisplayName("Integration: CORS should reject disallowed origin")
    void cors_shouldRejectDisallowedOrigin() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"smartphones\"}")
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



