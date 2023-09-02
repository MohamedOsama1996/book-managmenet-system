package com.banque.management.service.impl;

import com.banque.management.config.security.JwtValidator;
import com.banque.management.entity.BookEntity;
import com.banque.management.entity.BookGenreEntity;
import com.banque.management.entity.UserBorrowEntity;
import com.banque.management.entity.UsersEntity;
import com.banque.management.exception.GeneralException;
import com.banque.management.exception.ErrorResponse;
import com.banque.management.repository.BookRepository;
import com.banque.management.repository.UsersRepository;
import com.banque.management.requet.AddBookRequest;
import com.banque.management.requet.BorrowBookRequest;
import com.banque.management.requet.UpdateBookRequest;
import com.banque.management.requet.UpdateInventoryRequest;
import com.banque.management.service.IBookGenreService;
import com.banque.management.service.IBookService;
import com.banque.management.service.IUserBorrowService;
import com.banque.management.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements IBookService {

  @Autowired
  BookRepository bookRepository;

  @Autowired
  IBookGenreService iBookGenreService;

  @Autowired
  @Lazy
  IUserBorrowService userBorrowService;

  @Autowired
  JwtValidator jwtValidator;

  @Autowired
  UsersRepository usersRepository;

  @Autowired
  IUserService userService;


  @Override
  public BookEntity addBook(AddBookRequest addBookRequest) {

    BookGenreEntity bookGenreEntity = iBookGenreService.getBookGenre(addBookRequest.getBookGenre());
    List<BookEntity> books = bookRepository.findByBookNameAndBookGenre(addBookRequest.getBookName(), bookGenreEntity);

    if (books.size() > 0) {
      ErrorResponse errorResponse = new ErrorResponse("book with same name found", HttpStatus.CONFLICT);
      throw new GeneralException("book with same name found", errorResponse);
    }
    BookEntity bookEntity = new BookEntity(addBookRequest.getBookName(), 1, true,bookGenreEntity,0);
    bookRepository.save(bookEntity);
    return bookEntity;
  }

  @Override
  public BookEntity updateBook(Integer bookId, UpdateBookRequest updateBookRequest) {

    Optional<BookEntity> book = bookRepository.findById(bookId);

    if (book.isEmpty()) {
      ErrorResponse errorResponse = new ErrorResponse("book not found", HttpStatus.NOT_FOUND);
      throw new GeneralException("book not found", errorResponse);
    }

    if (updateBookRequest.getBookGenre() != null) {
      BookGenreEntity bookGenreEntity = iBookGenreService.getBookGenre(updateBookRequest.getBookGenre());
      book.get()
          .setBookGenre(bookGenreEntity);
    }
    if (updateBookRequest.getBookName() != null && updateBookRequest.getBookName()
                                                                    .length() > 0) {
      book.get()
          .setBookName(updateBookRequest.getBookName());
    }
    return bookRepository.save(book.get());
  }

  @Override
  public UserBorrowEntity borrowBook(String token,Integer bookId, BorrowBookRequest borrowBookRequest) {
    Optional<BookEntity> book = bookRepository.findById(bookId);
    token = token.substring(7);
    String userId = jwtValidator.getUserId(token);
    Optional<UsersEntity> usersEntity = usersRepository.findById(Integer.parseInt(userId));
    if(book.isEmpty()){
      ErrorResponse errorResponse = new ErrorResponse("book not found", HttpStatus.NOT_FOUND);
      throw new GeneralException("book not found", errorResponse);
    }
    if(book.get().getBookQuantity() == 0){
      ErrorResponse errorResponse = new ErrorResponse("book quantity is not already zero", HttpStatus.NOT_FOUND);
      throw new GeneralException("book quantity is already zero", errorResponse);
    }
    return userBorrowService.createTransaction(borrowBookRequest,usersEntity.get(),book.get());

  }

  @Override
  public BookEntity getBook(Integer bookId) {
    Optional<BookEntity> book = bookRepository.findById(bookId);
    if(book.isEmpty()){
      ErrorResponse errorResponse = new ErrorResponse("book not found", HttpStatus.NOT_FOUND);
      throw new GeneralException("book not found", errorResponse);
    }
    return  book.get();
  }

  @Override
  public BookEntity updateBookStock(Integer bookId, UpdateInventoryRequest updateInventoryRequest) {

    if(updateInventoryRequest.getQuantity()>1){
      ErrorResponse errorResponse = new ErrorResponse("please make sure that quantity is only one", HttpStatus.FORBIDDEN);
      throw new GeneralException("please make sure that quantity is only one", errorResponse);
    }
     Optional<BookEntity> book = bookRepository.findById(bookId);
     if(book.isEmpty()){
       ErrorResponse errorResponse = new ErrorResponse("book not found", HttpStatus.NOT_FOUND);
       throw new GeneralException("book not found", errorResponse);
     }
    List<UserBorrowEntity> booksList = userBorrowService.findBookInTransactions(book.get(),-1);
    if(booksList.size()>0){
      ErrorResponse errorResponse = new ErrorResponse("book is not yet returned", HttpStatus.CONFLICT);
      throw new GeneralException("book is not yet returned", errorResponse);
    }

    if(updateInventoryRequest.getQuantity()==0){
           book.get().setBookQuantity(0);
           book.get().setBookAvailability(false);
      }
    if(updateInventoryRequest.getQuantity()== 1){
      book.get().setBookQuantity(1);
      book.get().setBookAvailability(true);
    }
    return bookRepository.save(book.get());
  }

  @Override
  public Set<BookEntity> RecommendBooks(Integer userId, Pageable pageable) {

    UsersEntity user = userService.findUserById(userId);
    List<UserBorrowEntity> userBorrow = userBorrowService.findUserTransactions(user);
    Set<BookEntity> recommendedBooks = new HashSet<>();
    userBorrow.stream()
              .forEach(trx -> {
                Optional<BookEntity> book = bookRepository.findById(trx.getBookEntity()
                                                                       .getBookId());
                Page<BookEntity> paging= bookRepository.findAllByBookGenreOrderByBorrowTimesDesc(book.get()
                                                                                                     .getBookGenre(), pageable);
                recommendedBooks.addAll(paging.stream().toList());
              });

     return recommendedBooks;
  }

  @Override
  public void deleteBook(Integer bookId) {
     Optional<BookEntity> book = bookRepository.findById(bookId);
     if(book.isEmpty()){
       ErrorResponse errorResponse = new ErrorResponse("cannot find book", HttpStatus.NOT_FOUND);
       throw new GeneralException("cannot find book", errorResponse);
     }
      List<UserBorrowEntity> userBorrow = userBorrowService.findBookInTransactions(book.get(),-1);
      if(userBorrow.size()>0){
        ErrorResponse errorResponse = new ErrorResponse("cannot delete book because book is not returned", HttpStatus.FORBIDDEN);
        throw new GeneralException("cannot delete book because book is not returned\"", errorResponse);
      }
      bookRepository.deleteById(bookId);

  }

  @Override
  public List<BookEntity> getBooks(Pageable pageable) {
     return bookRepository.findAll(pageable).stream().toList();
  }

}
