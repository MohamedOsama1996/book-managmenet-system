package com.banque.management.service;

import com.banque.management.entity.BookGenreEntity;

public interface IBookGenreService {

  BookGenreEntity getBookGenre(String type);


  BookGenreEntity getBookGenreByGenreId(Integer id);
}
