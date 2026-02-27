package com.nozirev_service.nozirev_soe.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.cors.reactive.CorsWebFilter;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    private final CorsConfig corsConfig = new CorsConfig();

    @Test
    @DisplayName("CorsWebFilter bean should not be null")
    void corsWebFilter_shouldNotBeNull() {
        CorsWebFilter corsWebFilter = corsConfig.corsWebFilter();
        assertNotNull(corsWebFilter);
    }
}


