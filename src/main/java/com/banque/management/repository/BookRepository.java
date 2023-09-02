package com.banque.management.repository;

import com.banque.management.entity.BookEntity;
import com.banque.management.entity.BookGenreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

  List<BookEntity> findByBookNameAndBookGenre(String bookName, BookGenreEntity bookGenreEntity);

  Page<BookEntity> findAllByBookGenreOrderByBorrowTimesDesc(BookGenreEntity bookGenre, Pageable pageable);
}
