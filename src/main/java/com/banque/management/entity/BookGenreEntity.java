package com.banque.management.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "book_genres")
@Data
public class BookGenreEntity {

  @Id
  @Column(name = "genre_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genres_generator")
  @SequenceGenerator(name = "genres_generator", sequenceName = "genre_sequence",initialValue = 1,allocationSize = 1)
   private int genreId;

  @Column(name = "type")
  private String type;
}
