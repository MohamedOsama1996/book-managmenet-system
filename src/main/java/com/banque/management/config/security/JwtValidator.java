package com.banque.management.config.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {


  @Value("${secret.value}")
  public String secretKey;

   public boolean validateJwt(String token){
     try {
       Jwts
           .parser()
           .setSigningKey(secretKey)
           .parseClaimsJws(token);
       return true;
     }catch (Exception ex) {
       return false;
     }
   }

   public String getUserName(String token){
     return Jwts
         .parser()
         .setSigningKey(secretKey)
         .parseClaimsJws(token)
         .getBody()
         .getSubject();
   }

  public String getUserId(String token){
    return String.valueOf( Jwts
        .parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody()
        .get("userId"));
  }
}
