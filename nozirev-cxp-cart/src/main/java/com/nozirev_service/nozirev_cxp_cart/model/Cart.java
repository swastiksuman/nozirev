package com.nozirev_service.nozirev_cxp_cart.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Cart {

    private String cartId;
    private String userId;
    private List<CartItem> items = new ArrayList<>();
    private double totalAmount;
    private int totalCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Cart(String cartId, String userId) {
        this.cartId = cartId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void recalculateTotals() {
        this.totalAmount = items.stream()
                .mapToDouble(item -> item.getAmount() * item.getQuantity())
                .sum();
        this.totalCount = items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        this.updatedAt = LocalDateTime.now();
    }
}
