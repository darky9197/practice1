package com.tms.backend.service;

import com.tms.backend.model.OrderItem;
import com.tms.backend.model.RestaurantDetails;
import com.tms.backend.repo.OrderRepository;
import com.tms.backend.repo.RestaurantRepository;
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