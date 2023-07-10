package com.matheusgermano.greenshop.protocols;

import com.matheusgermano.greenshop.models.User;

public interface IAuthenticationAdapter {
  public String generateToken(User user);
  public boolean isValid(String token);
}
