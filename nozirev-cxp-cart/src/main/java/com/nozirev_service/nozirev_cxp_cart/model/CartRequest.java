package com.nozirev_service.nozirev_cxp_cart.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {

    @NotNull(message = "User ID is required")
    private String userId;

    @Valid
    private ProfileDetails profileDetails;

    @Valid
    private ShippingDetails shippingDetails;

    @Valid
    private CartItem item;
}
