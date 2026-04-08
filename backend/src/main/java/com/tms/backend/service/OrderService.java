package com.tms.backend.service;

import com.tms.backend.model.Order;
import com.tms.backend.model.OrderItem;
import com.tms.backend.model.RestaurantDetails;
import com.tms.backend.model.Users;
import com.tms.backend.repo.OrderRepository;
import com.tms.backend.repo.RestaurantRepository;
import com.tms.backend.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private UserRepository userRepo;

    @PostConstruct
    public void initDummyRestaurants() {
        if (restaurantRepo.count() == 0) {
            RestaurantDetails restaurant1 = new RestaurantDetails();
            restaurant1.setRestaurantName("Spicy Delight");
            restaurantRepo.save(restaurant1);

            RestaurantDetails restaurant2 = new RestaurantDetails();
            restaurant2.setRestaurantName("Ocean View Seafood");
            restaurantRepo.save(restaurant2);

            RestaurantDetails restaurant3 = new RestaurantDetails();
            restaurant3.setRestaurantName("Urban Grill");
            restaurantRepo.save(restaurant3);
        }
    }

    public RestaurantDetails getRestaurantDetails(UUID restaurantId) {
        return restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    public String placeOrder(UUID restaurantId, UUID userId, List<OrderItem> items) {
        RestaurantDetails restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setRestaurantDetails(restaurant);
        order.setUser(user);
        order.setStatus("PENDING");

        double totalAmount = 0.0;
        if (items != null) {
            for (OrderItem item : items) {
                item.setOrder(order);
                if (item.getPriceAtPurchase() != null && item.getQuantity() != null) {
                    totalAmount += (item.getPriceAtPurchase() * item.getQuantity());
                }
            }
            order.setItems(items);
        }

        order.setTotalAmount(totalAmount);
        orderRepo.save(order);

        return "Order placed successfully";
    }

    public Order confirmOrder(UUID orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("CONFIRMED");
        return orderRepo.save(order);
    }

    public Order rejectOrder(UUID orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("REJECTED");
        return orderRepo.save(order);
    }
}