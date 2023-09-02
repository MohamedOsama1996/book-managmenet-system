package com.banque.management.repository;


import com.banque.management.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


  @Repository
  public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

  }
