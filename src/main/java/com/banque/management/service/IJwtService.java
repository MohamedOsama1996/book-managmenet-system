package com.banque.management.service;

import java.util.Map;

public interface IJwtService {


  String generateToken(String userName,int userId);

  String createToken(Map<String,Object> claims,String username);
}
