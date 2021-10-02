package com.scm.backend.service;

import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.exception.ItemNumberAlreadyExistException;
import com.scm.backend.model.exception.ItemNumberLessThanOne;

public interface ItemService {
    void createItem(ItemDto itemDto) throws ItemNumberAlreadyExistException, ItemNumberLessThanOne;
}
