package com.banque.management.controller;

import com.banque.management.config.security.JwtValidator;
import com.banque.management.requet.AddBookRequest;
import com.banque.management.requet.BorrowBookRequest;
import com.banque.management.requet.UpdateBookRequest;
import com.banque.management.service.IBookService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {

  @Autowired
  IBookService iBookService;

  @Autowired
  JwtValidator validator;

  @PreAuthorize("hasRole('ROLE_admin')")
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ResponseEntity<?> addBook(@Valid @RequestBody AddBookRequest addBookRequest) {

    return new ResponseEntity<>(iBookService.addBook(addBookRequest), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ROLE_customer')")
  @RequestMapping(value = "/get-all", method = RequestMethod.GET)
  public ResponseEntity<?> getBooks(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
   Pageable pageable = PageRequest.of(page,size);
    return new ResponseEntity<>(iBookService.getBooks(pageable), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_admin')")
  @RequestMapping(value = "/update/{bookId}",method = RequestMethod.PUT)
  public ResponseEntity<?> updateBook(@PathVariable Integer bookId,@Valid @RequestBody UpdateBookRequest updateBookRequest){

    return new ResponseEntity<>(iBookService.updateBook(bookId,updateBookRequest),HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_customer')")
  @RequestMapping(value = "/{bookId}/borrow",method = RequestMethod.POST)
  public ResponseEntity<?> borrowBook(@RequestHeader("Authorization") @ApiParam(hidden = true) String token,@PathVariable Integer bookId,@Valid @RequestBody BorrowBookRequest borrowBookRequest){

    return new ResponseEntity<>(iBookService.borrowBook( token,bookId,borrowBookRequest),HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_customer')")
  @RequestMapping(value = "/recommend",method = RequestMethod.GET)
  public ResponseEntity<?> recommendBook(@RequestHeader("Authorization") @ApiParam(hidden = true) String token ,@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size){

    String userId = validator.getUserId(token.substring(7));
    Pageable pageable = PageRequest.of(page,size);
    return new ResponseEntity<>(iBookService.RecommendBooks(Integer.valueOf(userId),pageable),HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_admin')")
  @RequestMapping(value = "/{bookId}/delete",method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteBook(@PathVariable Integer bookId){
    iBookService.deleteBook(bookId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


}
