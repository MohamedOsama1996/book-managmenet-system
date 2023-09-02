package com.banque.management.service.impl;

import com.banque.management.entity.BookGenreEntity;
import com.banque.management.exception.ErrorResponse;
import com.banque.management.exception.GeneralException;
import com.banque.management.repository.BookGenreRepository;
import com.banque.management.service.IBookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BookGenreServiceImpl implements IBookGenreService {

  @Autowired
  BookGenreRepository bookGenreRepository;

  @Override
  public BookGenreEntity getBookGenre(String type) {

    Optional<BookGenreEntity> optionalBookGenreEntity = bookGenreRepository.findByType(type);
     if(optionalBookGenreEntity.isPresent()){
       return optionalBookGenreEntity.get();
     }
    ErrorResponse errorResponse = new ErrorResponse("genre not found ", HttpStatus.CONFLICT);
    throw new GeneralException("genre not found", errorResponse);
  }

  @Override
  public BookGenreEntity getBookGenreByGenreId(Integer id) {
       return bookGenreRepository.findById(id).orElseThrow(() ->{
         ErrorResponse errorResponse = new ErrorResponse("genre not found ", HttpStatus.NOT_FOUND);
         throw new GeneralException("genre not found", errorResponse);
       });
  }
}
