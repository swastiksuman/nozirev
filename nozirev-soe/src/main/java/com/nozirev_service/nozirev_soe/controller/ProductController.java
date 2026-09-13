package com.nozirev_service.nozirev_soe.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ObjectMapper objectMapper;
    private final Map<String, List<Product>> productsByCategory = new ConcurrentHashMap<>();

    public ProductController() {
        this.objectMapper = new ObjectMapper();
        loadProducts();
    }

    public ProductController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper != null ? objectMapper : new ObjectMapper();
        loadProducts();
    }

    @PostConstruct
    public void loadProducts() {
        try {
            ClassPathResource resource = new ClassPathResource("products.json");
            try (InputStream inputStream = resource.getInputStream()) {
                Map<String, List<Product>> data = objectMapper.readValue(
                        inputStream,
                        new TypeReference<Map<String, List<Product>>>() {}
                );
                productsByCategory.clear();
                if (data != null) {
                    data.forEach((key, value) -> productsByCategory.put(key.toLowerCase(), value));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        return productsByCategory.values().stream()
                .flatMap(List::stream)
                .filter(product -> product.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/getProductList")
    public List<Product> getProductList(@RequestBody ProductRequest request) {
        if (request == null || request.getType() == null) {
            return Collections.emptyList();
        }
        String type = request.getType().toLowerCase();
        return productsByCategory.getOrDefault(type, Collections.emptyList());
    }

    public static class Body {
        private String dimensions;
        private String weight;

        public Body() {}

        public Body(String dimensions, String weight) {
            this.dimensions = dimensions;
            this.weight = weight;
        }

        public String getDimensions() {
            return dimensions;
        }

        public void setDimensions(String dimensions) {
            this.dimensions = dimensions;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }
    }

    public static class Display {
        private String type;
        private String size;
        private String resolution;

        public Display() {}

        public Display(String type, String size, String resolution) {
            this.type = type;
            this.size = size;
            this.resolution = resolution;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getResolution() {
            return resolution;
        }

        public void setResolution(String resolution) {
            this.resolution = resolution;
        }
    }

    public static class Product {
        private int id;
        private String productName;
        private String imageUrl;
        private int amount;
        private String description;
        private String networkTechnology;
        private String launchDate;
        private Body body;
        private Display display;
        private String mainCamera;
        private String selfieCamera;

        public Product() {}

        public Product(int id, String productName, String imageUrl, int amount, String description) {
            this(id, productName, imageUrl, amount, description, null, null, null, null, null, null);
        }

        public Product(int id, String productName, String imageUrl, int amount, String description,
                       String networkTechnology, String launchDate, Body body, Display display,
                       String mainCamera, String selfieCamera) {
            this.id = id;
            this.productName = productName;
            this.imageUrl = imageUrl;
            this.amount = amount;
            this.description = description;
            this.networkTechnology = networkTechnology;
            this.launchDate = launchDate;
            this.body = body;
            this.display = display;
            this.mainCamera = mainCamera;
            this.selfieCamera = selfieCamera;
        }

        public int getId() {
            return id;
        }

        public String getProductName() {
            return productName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public int getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }

        public String getNetworkTechnology() {
            return networkTechnology;
        }

        public String getLaunchDate() {
            return launchDate;
        }

        public Body getBody() {
            return body;
        }

        public Display getDisplay() {
            return display;
        }

        public String getMainCamera() {
            return mainCamera;
        }

        public String getSelfieCamera() {
            return selfieCamera;
        }
    }

    private static class ProductRequest {
        private String type;

        public ProductRequest() {
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

