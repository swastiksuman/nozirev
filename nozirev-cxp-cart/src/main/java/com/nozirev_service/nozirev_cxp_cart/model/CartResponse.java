package com.nozirev_service.nozirev_cxp_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private boolean success;
    private String message;
    private Cart cart;

    public static CartResponse ok(String message, Cart cart) {
        return new CartResponse(true, message, cart);
    }

    public static CartResponse error(String message) {
        return new CartResponse(false, message, null);
    }
}
