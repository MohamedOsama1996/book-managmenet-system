package com.banque.management.service;

import com.banque.management.entity.UsersEntity;

public interface IUserService {

  UsersEntity findUser(String userName);

  UsersEntity findUserById(Integer userId);

}


