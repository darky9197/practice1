package com.tms.backend.controller;

import com.tms.backend.dto.OrderRequest;
import com.tms.backend.model.OrderItem;
import com.tms.backend.model.RestaurantDetails;
import com.tms.backend.service.OrderService;
import com.tms.backend.util.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public String placeOrder(@RequestBody OrderRequest request) {

        List<OrderItem> items = request.getItems().stream().map(itemReq -> {
            OrderItem item = new OrderItem();
            item.setQuantity(itemReq.getQuantity());
            item.setPriceAtPurchase(itemReq.getPriceAtPurchase());
            return item;
        }).toList();

        return orderService.placeOrder(
                request.getRestaurantId(),
                request.getUserId(),
                items
        );
    }

    @GetMapping("/{restaurantId}")
    public RestaurantDetails getRestaurant(@PathVariable UUID restaurantId) {
        return orderService.getRestaurantDetails(restaurantId);
    }
}