package com.scm.backend.service;

import com.scm.backend.model.dto.ItemTypeDto;
import com.scm.backend.model.entity.ItemType;

import java.util.Optional;

public interface ItemTypeService {
    Optional<ItemType> findItemTypeById(Long itemTypeId);
    void createItemType(ItemTypeDto itemTypeDto);
}
