package com.banque.management.requet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowBookRequest implements Serializable {

  @NotNull(message = "can't be null")
  private Date startDate;

  @NotNull(message = "can't be null")
  private Date endDate;






}
