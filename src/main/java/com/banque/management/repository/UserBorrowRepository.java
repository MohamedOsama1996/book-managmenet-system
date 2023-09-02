package com.banque.management.repository;

import com.banque.management.entity.BookEntity;
import com.banque.management.entity.UserBorrowEntity;
import com.banque.management.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserBorrowRepository extends JpaRepository<UserBorrowEntity, UUID> {

  List<UserBorrowEntity> findByBookEntity(BookEntity book);

  List<UserBorrowEntity> findByBookEntityAndReturned(BookEntity book,int returned);


  List<UserBorrowEntity> findByUsersEntity(UsersEntity user);
}
