package com.nozirev_service.nozirev_cxp_cart.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @NotNull(message = "Product ID is required")
    private Integer productId;

    @NotNull(message = "Product name is required")
    private String productName;

    @NotNull(message = "Image URL is required")
    private String imageUrl;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be non-negative")
    private Double amount;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    private String description;
}
