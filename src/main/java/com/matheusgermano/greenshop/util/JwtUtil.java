package com.matheusgermano.greenshop.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.matheusgermano.greenshop.models.User;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {
    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(String.valueOf(user.getId()));
        return JWT.create()
                .withIssuer("auth0")
                .withSubject(user.getName())
                .withClaim("id", String.valueOf(user.getId()))
                .sign(algorithm);
    }
}
