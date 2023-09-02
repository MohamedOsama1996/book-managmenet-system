package com.banque.management.repository;

import com.banque.management.entity.BookGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenreEntity,Integer> {


  Optional<BookGenreEntity> findByType(String type);
}
