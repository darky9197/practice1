package com.tms.backend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemRequest {

    private Integer quantity;
    private Double priceAtPurchase;

}
