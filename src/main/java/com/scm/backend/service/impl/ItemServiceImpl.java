package com.scm.backend.service.impl;

import com.google.common.collect.Sets;
import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.entity.ItemType;
import com.scm.backend.model.exception.*;
import com.scm.backend.repository.ItemRepository;
import com.scm.backend.repository.custom.ItemRepositoryCustom;
import com.scm.backend.service.ItemService;
import com.scm.backend.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemTypeService itemTypeService;

    @Override
    public void createItem(ItemDto itemDto) throws ItemNumberAlreadyExistException, ItemNumberLessThanOne, ItemTypeNotFoundException {
        checkBeforeCreate(itemDto);
        createNewItemWithDtoData(itemDto);
    }

    @Override
    public Item updateItem(ItemDto itemDto) throws ItemNumberNotFoundException, ConcurrentUpdateItemException, ItemTypeNotFoundException {
        Item item = checkBeforeUpdate(itemDto);
        return updateItemWithDtoData(item, itemDto);
    }

    private Item updateItemWithDtoData(Item item, ItemDto itemDto) throws ItemTypeNotFoundException {
        ItemType itemType = itemTypeService.findItemTypeById(itemDto.getItemType().getId())
                .orElseThrow(() -> new ItemTypeNotFoundException("Item type not found.", itemDto.getItemType().getTypeName()));


        item.setName(itemDto.getName());
        item.setState(itemDto.getState());
        item.setUpdateDate(itemDto.getUpdateDate());
        item.setQuantity(itemDto.getQuantity());
        item.setSalesPrice(itemDto.getSalesPrice());
        item.setCost(itemDto.getCost());
        item.setItemType(itemType);

        itemRepository.save(item);

        return item;
    }

    private void createNewItemWithDtoData(ItemDto itemDto) throws ItemTypeNotFoundException {
        Item item = createNewItem(itemDto);

        itemRepository.saveAndFlush(item);
    }

    private Item createNewItem(ItemDto itemDto) throws ItemTypeNotFoundException {
        ItemType itemType = itemTypeService.findItemTypeById(itemDto.getItemType().getId())
                .orElseThrow(() -> new ItemTypeNotFoundException("Item type not found.", itemDto.getItemType().getTypeName()));

        return Item.builder()
                .itemNumber(itemDto.getItemNumber())
                .name(itemDto.getName())
                .state(itemDto.getState())
                .addedDate(itemDto.getAddedDate())
                .updateDate(itemDto.getUpdateDate())
                .quantity(itemDto.getQuantity())
                .salesPrice(itemDto.getSalesPrice())
                .cost(itemDto.getCost())
                .itemType(itemType)
                .build()
                ;
    }

    private void checkBeforeCreate(ItemDto itemDto) throws ItemNumberAlreadyExistException, ItemNumberLessThanOne {
        if (itemRepository.findItemByItemNumber(itemDto.getItemNumber()).isPresent()) {
            throw new ItemNumberAlreadyExistException("Item number already exist", itemDto.getItemNumber());
        }

        if(itemDto.getItemNumber() < 1){
            throw new ItemNumberLessThanOne("Item number must be larger than 0", itemDto.getItemNumber());
        }
    }

    private Item checkBeforeUpdate(ItemDto itemDto) throws ItemNumberNotFoundException, ConcurrentUpdateItemException {
        final Item item = itemRepository.findById(itemDto.getId())
                .orElseThrow(()-> new ItemNumberNotFoundException("Item number not found.", itemDto.getItemNumber()));

        if(!Objects.equals(item.getVersion(), itemDto.getVersion())){
            throw new ConcurrentUpdateItemException("Cannot update item, version have been changed.", itemDto.getId());
        }

        return item;

    }
}
