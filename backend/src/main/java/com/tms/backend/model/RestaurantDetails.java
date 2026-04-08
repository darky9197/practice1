package com.tms.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurants")
@Data
public class RestaurantDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID restaurantId;
    private String restaurantName;

    // Use the variable name "restaurantDetails" from the child classes
    @OneToMany(mappedBy = "restaurantDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MenuItem> menuList = new ArrayList<>();
}
