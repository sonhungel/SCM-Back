package com.scm.backend.service;

import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.entity.Invoice;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.exception.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemService {
    void createItem(ItemDto itemDto) throws ItemNumberAlreadyExistException, ItemNumberLessThanOne, ItemTypeNotFoundException, SupplierNotFoundException, CreateException;
    Item updateItem(ItemDto itemDto) throws ItemNumberNotFoundException, ConcurrentUpdateItemException, ItemTypeNotFoundException;
    Item getItemByItemNumber(Integer itemNumber) throws ItemNumberNotFoundException;
    List<Item> findItemWithQuery(String searchString);

    Page<Item> getAllItemWithPage(int pageNumber);

    void deleteItems(List<Integer> itemNumbers);
}
