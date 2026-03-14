package com.nozirev_service.nozirev_soe.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @PostMapping("/getProductList")
    public List<Product> getProductList(@RequestBody ProductRequest request) {
        String productCategory = request.getType().toLowerCase();

        switch (productCategory) {
            case "smartphones":
                return getSmartphones();
            case "tablets":
                return getTablets();
            case "watches":
                return getWatches();
            case "routers":
                return getRouters();
            default:
                return List.of();
        }
    }

    private List<Product> getSmartphones() {
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

    private List<Product> getTablets() {
        return List.of(
                new Product(11, "iPad Air", "https://fdn2.gsmarena.com/vv/bigpic/apple-ipad-air-2024.jpg", 599, "iPad Air with M2 chip"),
                new Product(12, "iPad Pro 11", "https://fdn2.gsmarena.com/vv/bigpic/apple-ipad-pro-11-2024.jpg", 899, "iPad Pro 11-inch with M4"),
                new Product(13, "iPad Pro 13", "https://fdn2.gsmarena.com/vv/bigpic/apple-ipad-pro-13-2024.jpg", 1099, "iPad Pro 13-inch with M4"),
                new Product(14, "Galaxy Tab S9", "https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-tab-s9.jpg", 799, "Samsung Galaxy Tab S9"),
                new Product(15, "Galaxy Tab S9 Ultra", "https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-tab-s9-ultra.jpg", 1199, "Samsung Galaxy Tab S9 Ultra"),
                new Product(16, "Surface Pro 10", "https://fdn2.gsmarena.com/vv/bigpic/microsoft-surface-pro-10.jpg", 999, "Microsoft Surface Pro 10"),
                new Product(17, "Pixel Tablet", "https://fdn2.gsmarena.com/vv/bigpic/google-pixel-tablet.jpg", 499, "Google Pixel Tablet")
        );
    }

    private List<Product> getWatches() {
        return List.of(
                new Product(21, "Apple Watch Series 9", "https://fdn2.gsmarena.com/vv/bigpic/apple-watch-series-9.jpg", 399, "Apple Watch Series 9"),
                new Product(22, "Apple Watch Ultra 2", "https://fdn2.gsmarena.com/vv/bigpic/apple-watch-ultra-2.jpg", 799, "Apple Watch Ultra 2"),
                new Product(23, "Galaxy Watch 6", "https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-watch6.jpg", 299, "Samsung Galaxy Watch 6"),
                new Product(24, "Galaxy Watch 6 Classic", "https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-watch6-classic.jpg", 399, "Samsung Galaxy Watch 6 Classic"),
                new Product(25, "Pixel Watch 2", "https://fdn2.gsmarena.com/vv/bigpic/google-pixel-watch-2.jpg", 349, "Google Pixel Watch 2"),
                new Product(26, "Garmin Fenix 7", "https://fdn2.gsmarena.com/vv/bigpic/garmin-fenix-7.jpg", 699, "Garmin Fenix 7"),
                new Product(27, "Fitbit Sense 2", "https://fdn2.gsmarena.com/vv/bigpic/fitbit-sense-2.jpg", 249, "Fitbit Sense 2")
        );
    }

    private List<Product> getRouters() {
        return List.of(
                new Product(31, "TP-Link Archer AX73", "https://fdn2.gsmarena.com/vv/bigpic/tp-link-archer-ax73.jpg", 149, "Wi-Fi 6 Router"),
                new Product(32, "Netgear Nighthawk AX12", "https://fdn2.gsmarena.com/vv/bigpic/netgear-nighthawk-ax12.jpg", 399, "12-Stream Wi-Fi 6 Router"),
                new Product(33, "ASUS RT-AX88U", "https://fdn2.gsmarena.com/vv/bigpic/asus-rt-ax88u.jpg", 299, "Dual-band Wi-Fi 6 Gaming Router"),
                new Product(34, "Google Nest WiFi Pro", "https://fdn2.gsmarena.com/vv/bigpic/google-nest-wifi-pro.jpg", 199, "Wi-Fi 6E Mesh Router"),
                new Product(35, "Linksys Velop MX5300", "https://fdn2.gsmarena.com/vv/bigpic/linksys-velop-mx5300.jpg", 349, "Tri-band Mesh Wi-Fi 6 System"),
                new Product(36, "Eero Pro 6E", "https://fdn2.gsmarena.com/vv/bigpic/eero-pro-6e.jpg", 299, "Wi-Fi 6E Mesh Router"),
                new Product(37, "TP-Link Deco XE75", "https://fdn2.gsmarena.com/vv/bigpic/tp-link-deco-xe75.jpg", 249, "Tri-band Wi-Fi 6E Mesh System")
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

