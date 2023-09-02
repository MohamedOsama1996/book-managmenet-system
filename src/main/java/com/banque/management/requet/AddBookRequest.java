package com.banque.management.requet;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AddBookRequest implements Serializable {

  @NotNull
  @NotBlank
  private String bookName;

  @NotNull
  @NotBlank
  private String bookGenre;
}
