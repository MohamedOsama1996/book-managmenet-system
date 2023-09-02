package com.banque.management.requet;

import lombok.Data;
import java.io.Serializable;
import java.util.UUID;

@Data
public class UpdateBorrowRequest implements Serializable {



  private int accepted;

  private UUID trxId;
}
