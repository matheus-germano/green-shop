package com.matheusgermano.greenshop.protocols;

import java.security.NoSuchAlgorithmException;

public interface ICryptoAdapter {
  public String encrypt(String password) throws NoSuchAlgorithmException;
}
