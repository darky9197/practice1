package com.tms.backend.util;

import com.tms.backend.model.Users;
import com.tms.backend.repo.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JWTService {
    private static final String SECRET_KEY = "649831E6991F283CCB4FE704AF64EF5F084F212B1242E5DF823B5BC8B0F7E8D71A751B8C61EB98D16958A385D39C6F1C20FC7C6CA950D4D3B4AF71FACBCE6412";
    private static final Long VALIDITY = TimeUnit.MINUTES.toMillis(120);
    private final UserRepository repository;

    @Autowired
    public JWTService(UserRepository repository){
        this.repository = repository;
    }

    public String generateToken(UserDetails user){
        Users myUser = repository.findByEmail(user.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("username not found!!"));
        Map<String, String> claim = new HashMap<>();
        claim.put("name", myUser.getUsername());
        claim.put("userId", "" + myUser.getUserId());

        return Jwts.builder()
                .subject(user.getUsername())
                .claims(claim)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey(){
        byte[] key = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token){
        return extractClaims(token)
                .getSubject();
    }

    public boolean validateToken(String token){
        return extractClaims(token)
                .getExpiration()
                .after(Date.from(Instant.now()));
    }
}
