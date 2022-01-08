package com.scm.backend.service;

import com.scm.backend.model.dto.ItemTypeDto;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.entity.ItemType;
import com.scm.backend.model.exception.ConcurrentUpdateException;
import com.scm.backend.model.exception.ItemTypeNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ItemTypeService {
    Optional<ItemType> findItemTypeById(Long itemTypeId);
    ItemType createItemType(ItemTypeDto itemTypeDto) throws ConcurrentUpdateException, ItemTypeNotFoundException;
    Long getNewItemTypeId();
    List<ItemType> getAllActiveItemType();
}
