package com.banque.management.service;

import com.banque.management.common.dto.UserBorrowDTO;
import com.banque.management.entity.BookEntity;
import com.banque.management.entity.UserBorrowEntity;
import com.banque.management.entity.UsersEntity;
import com.banque.management.requet.BorrowBookRequest;
import com.banque.management.requet.UpdateBorrowRequest;
import java.util.List;
import java.util.UUID;

public interface IUserBorrowService {


  UserBorrowEntity createTransaction(BorrowBookRequest borrowBookRequest, UsersEntity user, BookEntity book);


  UserBorrowDTO updateTransaction( UpdateBorrowRequest updateBorrowRequest);

  List<UserBorrowEntity> findBookInTransactions(BookEntity book,Integer returned);

  void FinishTransaction(UUID trxId);

  List<UserBorrowEntity> findUserTransactions(UsersEntity user);

}
