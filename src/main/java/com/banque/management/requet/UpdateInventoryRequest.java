package com.banque.management.requet;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateInventoryRequest implements Serializable {

  @NotNull
  private Integer quantity;
}
