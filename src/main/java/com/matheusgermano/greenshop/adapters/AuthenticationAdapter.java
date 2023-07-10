package com.matheusgermano.greenshop.adapters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.matheusgermano.greenshop.models.User;
import com.matheusgermano.greenshop.protocols.IAuthenticationAdapter;

public class AuthenticationAdapter implements IAuthenticationAdapter {

  @Override
  public String generateToken(User user) {
    Algorithm algorithm = Algorithm.HMAC256("green@shop");
      return JWT.create()
        .withIssuer("auth0")
        .withSubject(user.getName())
        .withClaim("id", String.valueOf(user.getId()))
        .sign(algorithm);
  }

  @Override
  public boolean isValid(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256("green@shop");
      JWTVerifier verifier = JWT.require(algorithm).build();
      verifier.verify(token);

      return true;
    } catch (JWTVerificationException exception){
      return false;
    }
  }
}
