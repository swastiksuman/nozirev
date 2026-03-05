package com.nozirev_service.nozirev_cxp_cart.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class CorsConfigTest {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(@Autowired ApplicationContext context) {
        this.webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    @DisplayName("CORS preflight OPTIONS for allowed origin returns 200 with CORS headers")
    void corsPreFlight_allowedOrigin_shouldReturnCorsHeaders() {
        webTestClient.options().uri("/api/cart/addItem")
                .header("Origin", "http://localhost:3000")
                .header("Access-Control-Request-Method", "POST")
                .header("Access-Control-Request-Headers", "Content-Type")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Access-Control-Allow-Origin", "http://localhost:3000")
                .expectHeader().exists("Access-Control-Allow-Methods")
                .expectHeader().valueEquals("Access-Control-Allow-Credentials", "true");
    }

    @Test
    @DisplayName("CORS request from allowed origin includes CORS response headers")
    void cors_allowedOrigin_shouldIncludeCorsResponseHeaders() {
        webTestClient.post().uri("/api/cart/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"userId\":\"corsTest\"}")
                .header("Origin", "http://localhost:3000")
                .exchange()
                .expectHeader().valueEquals("Access-Control-Allow-Origin", "http://localhost:3000")
                .expectHeader().valueEquals("Access-Control-Allow-Credentials", "true");
    }

    @Test
    @DisplayName("CORS request from disallowed origin should not have CORS headers")
    void cors_disallowedOrigin_shouldNotHaveCorsHeaders() {
        webTestClient.post().uri("/api/cart/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"userId\":\"corsTest2\"}")
                .header("Origin", "http://evil.com")
                .exchange()
                .expectHeader().doesNotExist("Access-Control-Allow-Origin");
    }
}
