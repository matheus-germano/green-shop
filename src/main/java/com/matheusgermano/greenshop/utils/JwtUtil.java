package com.matheusgermano.greenshop.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.matheusgermano.greenshop.models.User;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {
    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256("green@shop");
        return JWT.create()
                .withIssuer("auth0")
                .withSubject(user.getName())
                .withClaim("id", String.valueOf(user.getId()))
                .sign(algorithm);
    }

    public boolean isValid(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Algorithm algorithm = Algorithm.HMAC256("green@shop");
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);

            return true;
        } catch (JWTVerificationException exception){
            return false;
        }
    }

    public String getTokenIdClaim(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            String tokenIdClaim = jwt.getClaim("id").asString();

            return tokenIdClaim;
        } catch (JWTDecodeException exception){
            return "";
        }
    }
}
