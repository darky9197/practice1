package com.tms.backend.controller;

import com.tms.backend.model.RestaurantDetails;
import com.tms.backend.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantDetails> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @PostMapping("/filtered")
    public List<RestaurantDetails> filterRestaurants(@RequestParam String keyword) {
        return restaurantService.filterRestaurants(keyword);
    }

}
