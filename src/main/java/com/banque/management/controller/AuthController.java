package com.banque.management.controller;

import com.banque.management.entity.UsersEntity;
import com.banque.management.model.AuthenticationModel;
import com.banque.management.requet.AuthRequest;
import com.banque.management.service.IJwtService;
import com.banque.management.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {


  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  IJwtService jwtService;

  @Autowired
  IUserService userService;

  @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
  ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest){
    Authentication authentication = authenticationManager.authenticate(new
                                                                           UsernamePasswordAuthenticationToken(
        authRequest.getUsername(), authRequest.getPassword()));
    UserDetails user = (UserDetails) authentication.getPrincipal();
    UsersEntity usersEntity = userService.findUser(user.getUsername());
    String token = jwtService.generateToken(user.getUsername(), usersEntity.getUsersId());
    return new ResponseEntity<>(new AuthenticationModel(token), HttpStatus.OK);
  }
}
