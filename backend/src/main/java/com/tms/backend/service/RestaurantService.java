package com.tms.backend.service;

import com.tms.backend.model.RestaurantDetails;
import com.tms.backend.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    public String register(RestaurantDetails restaurant) {
        Optional<RestaurantDetails> existing = repository.findByRestaurantName(restaurant.getRestaurantName());

        if (existing.isPresent()) {
            return "Restaurant already exists!";
        }

        repository.save(restaurant);
        return "Registered successfully";
    }

    // Returning all restaurants; the frontend will perform the search logic.
    public List<RestaurantDetails> getAllRestaurants() {
        return repository.findAll();
    }
}