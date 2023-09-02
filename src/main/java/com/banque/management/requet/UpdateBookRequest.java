package com.banque.management.requet;

import lombok.Data;
import org.springframework.lang.Nullable;
import java.io.Serializable;

@Data
public class UpdateBookRequest implements Serializable {


  @Nullable
  private String bookName;


  @Nullable
  private String bookGenre;

}
