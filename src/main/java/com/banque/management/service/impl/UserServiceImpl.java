package com.banque.management.service.impl;

import com.banque.management.entity.UsersEntity;
import com.banque.management.exception.ErrorResponse;
import com.banque.management.exception.GeneralException;
import com.banque.management.repository.UsersRepository;
import com.banque.management.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  UsersRepository usersRepository;

  @Override
  public UsersEntity findUser(String userName) {
     return usersRepository.findByUserName(userName).orElseThrow(() ->{
       ErrorResponse errorResponse = new ErrorResponse("user not found", HttpStatus.NOT_FOUND);
       throw new GeneralException("user not found", errorResponse);
     });
  }
  public UsersEntity findUserById(Integer userId){
     return  usersRepository.findById(userId).orElseThrow( () ->{
       ErrorResponse errorResponse = new ErrorResponse("user not found", HttpStatus.NOT_FOUND);
       throw new GeneralException("user not found", errorResponse);
     });
  }
}
