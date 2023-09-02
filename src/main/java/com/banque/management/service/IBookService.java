package com.banque.management.service;

import com.banque.management.entity.BookEntity;
import com.banque.management.entity.UserBorrowEntity;
import com.banque.management.requet.AddBookRequest;
import com.banque.management.requet.BorrowBookRequest;
import com.banque.management.requet.UpdateBookRequest;
import com.banque.management.requet.UpdateInventoryRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

public interface IBookService {

   BookEntity addBook(AddBookRequest addBookRequest);

   BookEntity updateBook(Integer bookId,UpdateBookRequest updateBookRequest);
   UserBorrowEntity borrowBook(String token,Integer bookId, BorrowBookRequest borrowBookRequest);

   BookEntity getBook(Integer bookId);

   BookEntity updateBookStock(Integer bookId, UpdateInventoryRequest updateInventoryRequest);

   Set<BookEntity> RecommendBooks(Integer userId, Pageable pageable);

   void deleteBook(Integer bookId);

   List<BookEntity> getBooks(Pageable pageable);

}
