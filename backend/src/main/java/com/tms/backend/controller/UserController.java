package com.tms.backend.controller;

import com.tms.backend.model.LoginDTO;
import com.tms.backend.model.Users;
import com.tms.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody Users user) {
//        System.out.println("something happened!!"+ userService.register(user));
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO user) {
        return ResponseEntity.ok(userService.verify(user));
    }

}
