package com.tms.backend.service;

import com.tms.backend.repo.UserRepository;
import com.tms.backend.model.Users;
import com.tms.backend.util.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;


    public Users register(Users user) {

        user.setPassword(encoder.encode(user.getPassword()));

        return repo.save(user);

    }

    public String verify(Users user) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        if(authentication.isAuthenticated()) {

            Users existing = repo.findByUsername(user.getUserName());

            return jwtService.generateToken(existing.getEmail());
        }
        return "Fail";

    }
}
