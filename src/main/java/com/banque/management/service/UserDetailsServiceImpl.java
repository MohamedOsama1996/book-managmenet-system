package com.banque.management.service;

import com.banque.management.entity.RoleEntity;
import com.banque.management.entity.UsersEntity;
import com.banque.management.exception.ErrorResponse;
import com.banque.management.exception.GeneralException;
import com.banque.management.repository.RoleRepository;
import com.banque.management.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UsersRepository usersRepository;

  @Autowired
  RoleRepository roleRepository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UsersEntity user =  usersRepository.findByUserName(username).orElseThrow(() ->{
      ErrorResponse errorResponse = new ErrorResponse("user not found", HttpStatus.NOT_FOUND);
      throw new GeneralException("user not found", errorResponse);
    });
    Optional<RoleEntity> role = roleRepository.findById(user.getRoleEntity().getRoleId());
    if(role.isEmpty()){
      ErrorResponse errorResponse = new ErrorResponse("role not found", HttpStatus.NOT_FOUND);
      throw new GeneralException("role not found", errorResponse);
    }
    return org.springframework.security.core.userdetails.User.builder()
                                                             .username(user.getUsername())
                                                             .password(user.getPassword())
                                                             .roles(role.get().getRole())
                                                             .build();
  }
}
