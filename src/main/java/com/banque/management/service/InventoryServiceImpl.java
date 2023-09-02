package com.banque.management.service;

import com.banque.management.entity.BookEntity;
import com.banque.management.requet.UpdateInventoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements IInventoryService {


  @Autowired
  IBookService bookService;
  @Override
  public BookEntity updateInventory(Integer bookId, UpdateInventoryRequest updateInventoryRequest) {
       return bookService.updateBookStock(bookId,updateInventoryRequest);
  }
}
