package com.tms.backend.service;

import com.tms.backend.model.LoginDTO;
import com.tms.backend.repo.UserRepository;
import com.tms.backend.model.Users;
import com.tms.backend.util.JWTService;
import com.tms.backend.util.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private final PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private MyUserDetailService myUserDetailService;

    public UserService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    public String register(Users user) {

        Optional<Users> emailCheck = repo.findByEmail(user.getEmail());
        if(emailCheck.isPresent()){
            return "user already exist";
        }

        String realPassword = user.getPassword();
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);

        try{
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            realPassword
                    )
            );
//            System.out.println("something happened!!");
            if(auth.isAuthenticated()){
                return jwtService.generateToken(
                        myUserDetailService.loadUserByUsername(user.getEmail())
                );
            }
        } catch (UsernameNotFoundException e){
            return "null";
        }

        return "null";

    }

    public String verify(LoginDTO login) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.email(),
                        login.password()
                )
        );
        if(auth.isAuthenticated()){
            return jwtService.generateToken(
                    myUserDetailService.loadUserByUsername(login.email())
            );
        }
        throw new UsernameNotFoundException("username not found!!");

    }
}
