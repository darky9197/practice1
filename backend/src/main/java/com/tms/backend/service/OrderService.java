package com.tms.backend.service;

import com.tms.backend.model.OrderItem;
import com.tms.backend.model.RestaurantDetails;
import com.tms.backend.repo.OrderRepository;
import com.tms.backend.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private RestaurantRepository restaurantRepo;

    public String placeOrder(UUID restaurantId, OrderItem order) {

        RestaurantDetails restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));


        restaurant.getOrderList().add(order);

        if (order.getPrice() != null && order.getQuantity() != null) {
            int total = order.getPrice() * order.getQuantity();
            order.setPrice(total);
        }

        orderRepo.save(order);

        return "Order placed successfully";
    }
}