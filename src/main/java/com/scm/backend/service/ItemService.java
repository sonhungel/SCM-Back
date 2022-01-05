package com.scm.backend.service;

import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.exception.*;

import java.util.List;

public interface ItemService {
    void createItem(ItemDto itemDto) throws ItemNumberAlreadyExistException, ItemNumberLessThanOne, ItemTypeNotFoundException, SupplierNotFoundException;
    Item updateItem(ItemDto itemDto) throws ItemNumberNotFoundException, ConcurrentUpdateItemException, ItemTypeNotFoundException;
    List<Item> getItemByItemNumber(Integer itemNumber) throws ItemNumberNotFoundException;
    List<Item> findItemWithQuery(String searchString);

}
