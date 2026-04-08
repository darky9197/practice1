package com.tms.backend.service;

import com.tms.backend.model.*;
import com.tms.backend.repo.MenuRepository;
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


    private OrderRepository orderRepo;

    private RestaurantRepository restaurantRepo;

    private UserRepository userRepo;

    private MenuRepository menuRepo;

    public OrderService(OrderRepository orderRepo, RestaurantRepository restaurantRepo, UserRepository userRepo, MenuRepository menuRepo) {
        this.orderRepo = orderRepo;
        this.restaurantRepo = restaurantRepo;
        this.userRepo = userRepo;
        this.menuRepo = menuRepo;
    }

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

        double totalAmount = 0.0;

        if (items != null) {
            for (OrderItem item : items) {
                item.setOrder(order);

                if (item.getPriceAtPurchase() != null && item.getQuantity() != null) {
                    totalAmount += item.getPriceAtPurchase() * item.getQuantity();
                }
            }
            order.setItems(items);
        }

        order.setTotalAmount(totalAmount);

        orderRepo.save(order);

        return "Order placed successfully";
    }
}