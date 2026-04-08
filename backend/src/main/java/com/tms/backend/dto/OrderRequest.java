package com.tms.backend.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequest {

    private UUID restaurantId;
    private UUID userId;
    private List<OrderItemRequest> items;

}
