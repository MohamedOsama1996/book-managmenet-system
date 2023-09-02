package com.banque.management.service;

import com.banque.management.entity.BookEntity;
import com.banque.management.requet.UpdateInventoryRequest;

public interface IInventoryService {


  BookEntity updateInventory(Integer bookId, UpdateInventoryRequest updateInventoryRequest);
}
