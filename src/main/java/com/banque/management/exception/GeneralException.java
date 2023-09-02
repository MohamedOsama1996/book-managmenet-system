package com.banque.management.exception;

import lombok.Data;

@Data
public class GeneralException extends RuntimeException {


  private ErrorResponse errorResponse;
   public GeneralException(String message,ErrorResponse errorResponse){
        super(message);
        this.errorResponse = errorResponse;
   }
}
