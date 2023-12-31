package com.banque.management.repository;

import com.banque.management.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Integer> {

   Optional<UsersEntity> findByUserName(String userName);
}
