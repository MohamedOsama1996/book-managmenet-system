package com.banque.management.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserBorrowDTO {


  private UUID trxId;

  private Date startDate;

  private Date returnDate;

  private UserEntityDTO user;

  private BookEntityDTO book;

  private int accepted;

  private int returned;
}
