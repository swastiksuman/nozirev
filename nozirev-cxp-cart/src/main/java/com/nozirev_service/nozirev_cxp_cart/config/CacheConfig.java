package com.nozirev_service.nozirev_cxp_cart.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Caffeine cache is auto-configured via application.properties
    // No manual bean definition needed when spring.cache.type=caffeine
}


