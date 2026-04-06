package com.tms.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    private String userName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> orderList;

}
