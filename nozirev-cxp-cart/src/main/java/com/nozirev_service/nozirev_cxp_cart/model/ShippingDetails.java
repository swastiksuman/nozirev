package com.nozirev_service.nozirev_cxp_cart.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShippingDetails {

    private String addressLine1;
    private String addressLine2;
    private String state;
    private String pinCode;
    private String deliveryType;
}

