package com.scm.backend.service.impl;

import com.scm.backend.model.dto.ItemTypeDto;
import com.scm.backend.model.entity.ItemType;
import com.scm.backend.model.exception.ConcurrentUpdateException;
import com.scm.backend.model.exception.ItemTypeNotFoundException;
import com.scm.backend.repository.ItemTypeRepository;
import com.scm.backend.service.ItemTypeService;
import com.scm.backend.util.ItemTypeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
    public ItemType createItemType(ItemTypeDto itemTypeDto) throws ConcurrentUpdateException, ItemTypeNotFoundException {
        checkBeforeCreateItemType(itemTypeDto);
        return createNewItemWithDtoData(itemTypeDto);
    }

    @Override
    public Long getNewItemTypeId() {
        ItemType itemType = createNewItemType();
        itemTypeRepository.saveAndFlush(itemType);

        return itemType.getId();
    }

    @Override
    public List<ItemType> getAllActiveItemType() {
        return itemTypeRepository.findByState(ItemTypeState.ACTIVE);
    }

    private ItemType createNewItemWithDtoData(ItemTypeDto itemTypeDto) throws ConcurrentUpdateException, ItemTypeNotFoundException {
        ItemType itemType = createNewItemTypeWithData(itemTypeDto);
        itemTypeRepository.saveAndFlush(itemType);

        return itemType;
    }

    private ItemType createNewItemTypeWithData(ItemTypeDto itemTypeDto) throws ItemTypeNotFoundException, ConcurrentUpdateException {
        ItemType itemType = itemTypeRepository.findById(itemTypeDto.getId())
                .orElseThrow(() -> new ItemTypeNotFoundException("Item type not found", itemTypeDto.getTypeName()));

        if(!Objects.equals(itemType.getVersion(), itemTypeDto.getVersion())){
            throw new ConcurrentUpdateException("Cannot update, version have been changed.");
        }

        itemType.setTypeName(itemTypeDto.getTypeName());
        itemType.setDescription(itemTypeDto.getDescription());
        itemType.setState(ItemTypeState.ACTIVE);
        itemType.setUpdateDate(LocalDate.now());

        return itemType;
    }

    private ItemType createNewItemType() {
        return ItemType.builder()
                .typeName("INACTIVE")
                .state(ItemTypeState.INACTIVE)
                .addedDate(LocalDate.now())
                .build()
                ;
    }

    private void checkBeforeCreateItemType(ItemTypeDto itemTypeDto) {
        //TODO: nothing todo now
    }
}
