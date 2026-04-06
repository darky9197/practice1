package com.tms.backend.repo;

import com.tms.backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    Users findByEmail(String email);

    Users findByUsername(String username);
}
