package com.nozirev_service.nozirev_soe.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductControllerTest {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        ProductController productController = new ProductController();
        webTestClient = WebTestClient.bindToController(productController).build();
    }

    @Test
    @DisplayName("POST /api/getProductList with smartphones should return 7 products")
    void getProductList_withSmartphones_shouldReturnSevenProducts() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"smartphones\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(7);
    }

    @Test
    @DisplayName("POST /api/getProductList with smartphones should return products from iPhone 11 to iPhone 17")
    void getProductList_withSmartphones_shouldReturnCorrectProductNames() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"smartphones\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].productName").isEqualTo("iPhone 11")
                .jsonPath("$[1].productName").isEqualTo("iPhone 12")
                .jsonPath("$[2].productName").isEqualTo("iPhone 13")
                .jsonPath("$[3].productName").isEqualTo("iPhone 14")
                .jsonPath("$[4].productName").isEqualTo("iPhone 15")
                .jsonPath("$[5].productName").isEqualTo("iPhone 16")
                .jsonPath("$[6].productName").isEqualTo("iPhone 17");
    }

    @Test
    @DisplayName("POST /api/getProductList with tablets should return 7 products")
    void getProductList_withTablets_shouldReturnSevenProducts() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"tablets\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(7);
    }

    @Test
    @DisplayName("POST /api/getProductList with tablets should return correct first product")
    void getProductList_withTablets_shouldReturnCorrectFirstProduct() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"tablets\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].productName").isEqualTo("iPad Air")
                .jsonPath("$[0].id").isEqualTo(11);
    }

    @Test
    @DisplayName("POST /api/getProductList with watches should return 7 products")
    void getProductList_withWatches_shouldReturnSevenProducts() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"watches\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(7);
    }

    @Test
    @DisplayName("POST /api/getProductList with watches should return correct first product")
    void getProductList_withWatches_shouldReturnCorrectFirstProduct() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"watches\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].productName").isEqualTo("Apple Watch Series 9")
                .jsonPath("$[0].id").isEqualTo(21);
    }

    @Test
    @DisplayName("POST /api/getProductList with routers should return 7 products")
    void getProductList_withRouters_shouldReturnSevenProducts() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"routers\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(7);
    }

    @Test
    @DisplayName("POST /api/getProductList with routers should return correct first product")
    void getProductList_withRouters_shouldReturnCorrectFirstProduct() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"routers\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].productName").isEqualTo("TP-Link Archer AX73")
                .jsonPath("$[0].id").isEqualTo(31);
    }

    @Test
    @DisplayName("POST /api/getProductList with smartphones should return correct IDs from 1 to 7")
    void getProductList_withSmartphones_shouldReturnCorrectIds() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"smartphones\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[6].id").isEqualTo(7);
    }

    @Test
    @DisplayName("POST /api/getProductList with smartphones should return correct prices")
    void getProductList_withSmartphones_shouldReturnCorrectPrices() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"smartphones\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].amount").isEqualTo(599)
                .jsonPath("$[1].amount").isEqualTo(699)
                .jsonPath("$[2].amount").isEqualTo(799)
                .jsonPath("$[3].amount").isEqualTo(899)
                .jsonPath("$[4].amount").isEqualTo(999)
                .jsonPath("$[5].amount").isEqualTo(1099)
                .jsonPath("$[6].amount").isEqualTo(1199);
    }

    @Test
    @DisplayName("POST /api/getProductList should return products with all required fields")
    void getProductList_shouldReturnProductsWithAllFields() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"smartphones\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").exists()
                .jsonPath("$[0].productName").exists()
                .jsonPath("$[0].imageUrl").exists()
                .jsonPath("$[0].amount").exists()
                .jsonPath("$[0].description").exists();
    }

    @Test
    @DisplayName("POST /api/getProductList should return valid image URLs")
    void getProductList_shouldReturnValidImageUrls() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"smartphones\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].imageUrl").value(url -> {
                    assertTrue(((String) url).contains("https://"), "URL should start with https://");
                    assertTrue(((String) url).contains("iphone-11"), "First product URL should contain iphone-11");
                })
                .jsonPath("$[6].imageUrl").value(url -> {
                    assertTrue(((String) url).contains("iphone-17"), "Last product URL should contain iphone-17");
                });
    }

    @Test
    @DisplayName("POST /api/getProductList should return non-empty descriptions")
    void getProductList_shouldReturnNonEmptyDescriptions() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"smartphones\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].description").isNotEmpty()
                .jsonPath("$[1].description").isNotEmpty()
                .jsonPath("$[2].description").isNotEmpty()
                .jsonPath("$[3].description").isNotEmpty()
                .jsonPath("$[4].description").isNotEmpty()
                .jsonPath("$[5].description").isNotEmpty()
                .jsonPath("$[6].description").isNotEmpty();
    }

    @Test
    @DisplayName("POST /api/getProductList should return Content-Type application/json")
    void getProductList_shouldReturnJsonContentType() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"smartphones\"}")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json");
    }

    @Test
    @DisplayName("POST /api/getProductList with invalid type should return empty list")
    void postProductList_withInvalidType_shouldReturnEmptyList() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"invalid\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0);
    }

    @Test
    @DisplayName("POST /api/getProductList with case insensitive type should work")
    void postProductList_withCaseInsensitiveType_shouldWork() {
        webTestClient.post()
                .uri("/api/getProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"type\":\"SMARTPHONES\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(7);
    }

    @Test
    @DisplayName("GET /api/getProductList should return 405 Method Not Allowed")
    void getProductList_shouldReturnMethodNotAllowed() {
        webTestClient.get()
                .uri("/api/getProductList")
                .exchange()
                .expectStatus().isEqualTo(405);
    }
}



