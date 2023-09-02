package com.banque.management.controller;

import com.banque.management.requet.UpdateBorrowRequest;
import com.banque.management.service.IUserBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  IUserBorrowService userBorrowService;

  @PreAuthorize("hasRole('ROLE_admin')")
  @RequestMapping(value = "/update",method = RequestMethod.PUT)
  ResponseEntity<?> updateRequest(@RequestBody UpdateBorrowRequest updateBorrowRequest){

    return new ResponseEntity<>(userBorrowService.updateTransaction(updateBorrowRequest), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_admin')")
  @RequestMapping(value = "/return/{trxId}",method = RequestMethod.PUT)
  ResponseEntity<?> returnTrx(@PathVariable UUID trxId){
   userBorrowService.FinishTransaction(trxId);
    return new ResponseEntity<>(HttpStatus.OK);
  }


}
