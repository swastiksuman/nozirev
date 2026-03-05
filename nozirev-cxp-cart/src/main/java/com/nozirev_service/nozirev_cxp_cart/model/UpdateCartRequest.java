package com.nozirev_service.nozirev_cxp_cart.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartRequest {

    @NotNull(message = "User ID is required")
    private String userId;

    @NotNull(message = "Product ID is required")
    private Integer productId;

    @Min(value = 0, message = "Quantity must be 0 or more (0 to remove)")
    private int quantity;
}
