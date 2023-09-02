package com.banque.management.controller;

import com.banque.management.requet.UpdateInventoryRequest;
import com.banque.management.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

  @Autowired
  IInventoryService inventoryService;

  @PreAuthorize("hasRole('ROLE_admin')")
  @RequestMapping(value = "/change-stock/{bookId}",method = RequestMethod.PUT)
  public ResponseEntity<?> changeStock(@PathVariable Integer bookId,@Valid @RequestBody UpdateInventoryRequest updateInventoryRequest){
          return new ResponseEntity<>(inventoryService.updateInventory(bookId,updateInventoryRequest), HttpStatus.OK);
  }
}
