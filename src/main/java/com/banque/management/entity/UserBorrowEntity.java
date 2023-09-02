package com.banque.management.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "users_borrow")
@Data
@NoArgsConstructor
public class UserBorrowEntity {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "trx_Id")
  private UUID trxId;

  @Column(name = "start_date")
  @NotNull(message = "start date cant be null")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date startDate;

  public UserBorrowEntity(Date startDate, Date returnDate, UsersEntity usersEntity, BookEntity bookEntity, int accepted,
      int returned) {
    this.startDate = startDate;
    this.returnDate = returnDate;
    this.usersEntity = usersEntity;
    this.bookEntity = bookEntity;
    this.accepted = accepted;
    this.returned = returned;
  }

  @Column(name = "return_date")
  @NotNull(message = "return date cant be null")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date returnDate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "users_id",referencedColumnName = "users_id")
  @JsonIgnore
  private UsersEntity usersEntity;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "books_id",referencedColumnName = "book_id")
  private BookEntity bookEntity;

  @Column(name = "accepted")
  private int accepted;

  @Column(name = "returned")
  private int returned;



}
