package com.luisgoes.ecommerce.ecommerceapi.entities.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    AWAITING_PAYMENT(1),
    PAID(2),
    PENDING(3),
    SHIPPED(4),
    DELIVERED(5),
    CANCELED(6),
    RETURNED(7),
    FAILED(8);

    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public static OrderStatus valueOf(int code) {
        for (OrderStatus value : OrderStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus code.");
    }

}