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
                new Product(1, "iPhone 11", "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-11.jpg", 599, "iPhone 11 with dual camera"),
                new Product(2, "iPhone 12", "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-12.jpg", 699, "iPhone 12 with 5G support"),
                new Product(3, "iPhone 13", "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-13.jpg", 799, "iPhone 13 with improved battery"),
                new Product(4, "iPhone 14", "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-14.jpg", 899, "iPhone 14 with crash detection"),
                new Product(5, "iPhone 15", "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-15.jpg", 999, "iPhone 15 with USB-C"),
                new Product(6, "iPhone 16", "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-16.jpg", 1099, "iPhone 16 with advanced AI"),
                new Product(7, "iPhone 17", "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-17.jpg", 1199, "Latest iPhone 17 model")
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