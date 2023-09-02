package com.banque.management.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


  @ExceptionHandler(GeneralException.class)
  public ResponseEntity<Object> handleBookNotFoundException(GeneralException ex){


    return new ResponseEntity<>(ex.getErrorResponse(),ex.getErrorResponse()
                                                        .getHttpStatus());

  }


}
