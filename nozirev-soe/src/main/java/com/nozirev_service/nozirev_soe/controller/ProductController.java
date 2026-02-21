package com.nozirev_service.nozirev_soe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @GetMapping("/getProductList")
    public List<Product> getProductList() {
        return List.of(
                new Product(1, "iPhone 15", "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-15.jpg", 999, "Latest iPhone model")
        );
    }

    private static class Product {
        private int id;
        private String productName;
        private String imageUrl;
        private int amount;
        private String description;

        public Product(int id, String productName, String imageUrl, int amount, String description) {
            this.id = id;
            this.productName = productName;
            this.imageUrl = imageUrl;
            this.amount = amount;
            this.description = description;
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
    }
}