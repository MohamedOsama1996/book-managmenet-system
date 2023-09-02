package com.banque.management.config.security;

import com.banque.management.exception.ErrorResponse;
import com.banque.management.exception.GeneralException;
import com.banque.management.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  @Autowired
  JwtValidator jwtValidator;

  @Autowired
  UserDetailsService userDetailsService;



  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {


     String header = request.getHeader(Utils.AUTHORIZATION);

     if(header != null && header.startsWith("Bearer ")){
       String token = header.substring(7);

       if(!jwtValidator.validateJwt(token)){
         filterChain.doFilter(request,response);
         return;
       }

       String userName = jwtValidator.getUserName(token);

       if(userName!=null && SecurityContextHolder.getContext().getAuthentication() == null){
         UserDetails userDetails = null;
         try {
           userDetails = userDetailsService.loadUserByUsername(userName);
         }catch (Exception ex){
           ErrorResponse errorResponse = new ErrorResponse("user not found", HttpStatus.CONFLICT);
           throw new GeneralException("user not found", errorResponse);
         }
         setAuthenticationContext(userDetails);
       }

     }
     filterChain.doFilter(request,response);



  }

  private void setAuthenticationContext(UserDetails userDetails) {

    UsernamePasswordAuthenticationToken
        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities().stream().toList());

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
