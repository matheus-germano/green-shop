package com.matheusgermano.greenshop.adapters;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.matheusgermano.greenshop.protocols.ICryptoAdapter;

public class CryptoAdapter implements ICryptoAdapter {

  @Override
  public String encrypt(String password) throws NoSuchAlgorithmException {
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    byte[] hashedPasswordBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
    StringBuilder stringBuilder = new StringBuilder();

    for (byte b : hashedPasswordBytes) {
        stringBuilder.append(String.format("%02x", b));
    }

    return stringBuilder.toString();
  }
  
}
