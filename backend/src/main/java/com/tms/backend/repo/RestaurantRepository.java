package com.tms.backend.repo;

import com.tms.backend.model.RestaurantDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantDetails, UUID> {
    Optional<RestaurantDetails> findByRestaurantName(String restaurantName);
    List<RestaurantDetails> findByRestaurantNameContainingIgnoreCase(String keyword);
}
