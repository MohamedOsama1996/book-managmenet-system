package com.banque.management.service.impl;

import com.banque.management.common.dto.BookEntityDTO;
import com.banque.management.common.dto.UserBorrowDTO;
import com.banque.management.common.dto.UserEntityDTO;
import com.banque.management.entity.BookEntity;
import com.banque.management.entity.UserBorrowEntity;
import com.banque.management.entity.UsersEntity;
import com.banque.management.exception.GeneralException;
import com.banque.management.exception.ErrorResponse;
import com.banque.management.repository.BookRepository;
import com.banque.management.repository.UserBorrowRepository;
import com.banque.management.requet.BorrowBookRequest;
import com.banque.management.requet.UpdateBorrowRequest;
import com.banque.management.service.IBookService;
import com.banque.management.service.IUserBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserBorrowServiceImpl implements IUserBorrowService {

  @Autowired
  UserBorrowRepository userBorrowRepository;

  @Autowired
  IBookService bookService;

  @Autowired
  BookRepository bookRepository;

  @Override
  public UserBorrowEntity createTransaction(BorrowBookRequest borrowBookRequest, UsersEntity user, BookEntity book) {

     if(Date.valueOf(borrowBookRequest.getStartDate().toLocalDate()).after(Date.valueOf(borrowBookRequest.getEndDate().toLocalDate()))){

     }
     UserBorrowEntity userBorrowEntity = new UserBorrowEntity(Date.valueOf(borrowBookRequest.getStartDate().toLocalDate()),
                                                              Date.valueOf(borrowBookRequest.getEndDate().toLocalDate())
                                                              ,user,book,0,0);

     return userBorrowRepository.save(userBorrowEntity);
  }

  @Override
  public UserBorrowDTO updateTransaction( UpdateBorrowRequest updateBorrowRequest) {

    Optional<UserBorrowEntity> userBorrow = userBorrowRepository.findById(updateBorrowRequest.getTrxId());
    if(userBorrow.isEmpty()){
      ErrorResponse errorResponse = new ErrorResponse("transaction not found",HttpStatus.NOT_FOUND);
      throw new GeneralException("transaction not found",errorResponse);
    }
    if(userBorrow.isPresent()){
       if(Date.valueOf(LocalDate.now()).after(userBorrow.get().getReturnDate()) ||
       userBorrow.get().getStartDate().before(Date.valueOf(LocalDate.now())) ||
       userBorrow.get().getReturnDate().before(Date.valueOf(LocalDate.now()))){
         ErrorResponse errorResponse = new ErrorResponse("error in dates",HttpStatus.BAD_REQUEST);
         throw new GeneralException("error in dates",errorResponse);
       }
    }
    if(userBorrow.get().getReturned() ==1){
      ErrorResponse errorResponse = new ErrorResponse("transaction already completed",HttpStatus.UNPROCESSABLE_ENTITY);
      throw new GeneralException("transaction already completed",errorResponse);
    }
    if(updateBorrowRequest.getAccepted() == 1){
      BookEntity book = bookService.getBook(userBorrow.get().getBookEntity().getBookId());
      if(book.getBookQuantity() ==0){
        ErrorResponse errorResponse = new ErrorResponse("book quantity is zero already",HttpStatus.NOT_FOUND);
        throw new GeneralException("book quantity is zero already", errorResponse);
      }
      book.setBookAvailability(false);
      book.setBookQuantity(0);
      book.setBorrowTimes(book.getBorrowTimes()+1);
      bookRepository.save(book);
      userBorrow.get().setAccepted(1);
      userBorrow.get().setReturned(-1);
    }
    if(updateBorrowRequest.getAccepted()==-1){
      userBorrow.get().setAccepted(-1);
    }
    UserEntityDTO userEntityDTO = new UserEntityDTO(userBorrow.get().getUsersEntity().getUsersId(),userBorrow.get().getUsersEntity()
        .getUsername());

    BookEntityDTO bookEntityDTO = new BookEntityDTO(userBorrow.get().getBookEntity().getBookId(),
                                                    userBorrow.get().getBookEntity().getBookName());

    return new UserBorrowDTO(userBorrow.get().getTrxId(),
                             userBorrow.get().getStartDate(),userBorrow.get().getReturnDate(),
                             userEntityDTO,bookEntityDTO,userBorrow.get().getAccepted(),
                             userBorrow.get().getReturned());

  }

  @Override
  public List<UserBorrowEntity> findBookInTransactions(BookEntity book, Integer returned) {
    return userBorrowRepository.findByBookEntityAndReturned(book,returned);
  }

  @Override
  public void FinishTransaction(UUID trxId) {
    Optional<UserBorrowEntity> userBorrow = userBorrowRepository.findById(trxId);
    if(userBorrow.isEmpty()){
      ErrorResponse errorResponse = new ErrorResponse("transaction not found",HttpStatus.NOT_FOUND);
      throw new GeneralException("transaction not found",errorResponse);
    }
    userBorrow.get().setReturned(1);
    userBorrowRepository.save(userBorrow.get());
    BookEntity book =bookService.getBook(userBorrow.get().getBookEntity().getBookId());
    book.setBookAvailability(true);
    book.setBookQuantity(1);
    bookRepository.save(book);

  }

  @Override
  public List<UserBorrowEntity> findUserTransactions(UsersEntity user) {
     return  userBorrowRepository.findByUsersEntity(user);
  }
}
