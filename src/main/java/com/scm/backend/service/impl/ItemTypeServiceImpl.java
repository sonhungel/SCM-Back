package com.scm.backend.service.impl;

import com.scm.backend.model.dto.ItemTypeDto;
import com.scm.backend.model.entity.ItemType;
import com.scm.backend.repository.ItemTypeRepository;
import com.scm.backend.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItemTypeServiceImpl implements ItemTypeService {

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    @Override
    public Optional<ItemType> findItemTypeById(Long itemTypeId) {
        return itemTypeRepository.findById(itemTypeId);
    }

    @Override
    public void createItemType(ItemTypeDto itemTypeDto) {
        checkBeforeCreateItemType(itemTypeDto);
        createNewItemWithDtoData(itemTypeDto);
    }

    private void createNewItemWithDtoData(ItemTypeDto itemTypeDto) {
        ItemType itemType = createNewItemType(itemTypeDto);
        itemTypeRepository.saveAndFlush(itemType);
    }

    private ItemType createNewItemType(ItemTypeDto itemTypeDto) {
        return ItemType.builder()
                .typeName(itemTypeDto.getTypeName())
                .build()
                ;
    }

    private void checkBeforeCreateItemType(ItemTypeDto itemTypeDto) {
        //TODO: nothing todo now
    }
}
