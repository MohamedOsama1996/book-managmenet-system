package com.banque.management.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "book")
@Data
public class BookEntity {

  @Id
  @Column(name = "book_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
  @SequenceGenerator(name = "book_generator", sequenceName = "book_sequence",initialValue = 1,allocationSize = 1)
  private int bookId;

  @Column(name ="book_name")
  private String bookName;

  @Column(name = "book_quantity")
  private int bookQuantity;

  public BookEntity() {
  }

  public BookEntity(String bookName, int bookQuantity,boolean bookAvailability, BookGenreEntity bookGenre,Integer borrowTimes) {
    this.bookName = bookName;
    this.bookQuantity = bookQuantity;
    this.bookGenre = bookGenre;
    this.bookAvailability = bookAvailability;
    this.borrowTimes = borrowTimes;
  }

  @Column(name = "book_availability")
  private boolean bookAvailability;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "book_genre",referencedColumnName = "genre_id")
  private BookGenreEntity bookGenre;


  @Column(name = "borrow_times")
  private Integer borrowTimes;


}
