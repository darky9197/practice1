package com.tms.backend.service;

import com.tms.backend.model.MenuItem;
import com.tms.backend.model.RestaurantDetails;
import com.tms.backend.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    public List<RestaurantDetails> getAllRestaurants() {
        return repository.findAll();
    }

    public List<RestaurantDetails> filterRestaurants(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return repository.findAll();
        }
        return repository.findByRestaurantNameContainingIgnoreCase(keyword);
    }

    public List<MenuItem> getMenuItemsForRestaurant(UUID restaurantId) {
        RestaurantDetails restaurant = repository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return restaurant.getMenuList();
    }
}