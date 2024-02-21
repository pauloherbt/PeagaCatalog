package com.example.peagacatalog.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.peagacatalog.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    @Value("${spring.security.token.secret}")
    private String secret;
    public String createToken(User user){
         var algorithm = Algorithm.HMAC256(secret);
         return JWT.create().withIssuer("peagaCatalog")
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plus(24, ChronoUnit.HOURS))
                .sign(algorithm);
    }

    public String validateToken(String token){
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("peagaCatalog")
                .build()
                .verify(token).getSubject();
    }
}
