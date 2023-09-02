package com.banque.management.service.impl;

import com.banque.management.service.IJwtService;
import com.banque.management.utility.Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements IJwtService {

  @Value("${secret.value}")
  public String secretKey;

  public String generateToken(String userName,int userId){
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId",userId);
    return createToken(claims,userName);
  }

  public String createToken(Map<String,Object> claims,String username){

    return Jwts.builder()
               .setClaims(claims)
               .setSubject(username)
                .setIssuer(Utils.ISSUER)
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis()+Utils.EXPIRATION_DURATION))
               .signWith(SignatureAlgorithm.HS256, secretKey).compact();

  }
}
