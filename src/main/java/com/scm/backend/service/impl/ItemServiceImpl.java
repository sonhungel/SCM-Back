package com.scm.backend.service.impl;

import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.exception.ItemNumberAlreadyExistException;
import com.scm.backend.model.exception.ItemNumberLessThanOne;
import com.scm.backend.repository.ItemRepository;
import com.scm.backend.repository.custom.ItemRepositoryCustom;
import com.scm.backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void createItem(ItemDto itemDto) throws ItemNumberAlreadyExistException, ItemNumberLessThanOne {
        checkBeforeCreate(itemDto);
        createNewItemWithDtoData(itemDto);
    }

    private void createNewItemWithDtoData(ItemDto itemDto) {
        Item item = createNewItem(itemDto);

        itemRepository.saveAndFlush(item);
    }

    private Item createNewItem(ItemDto itemDto) {
        return Item.builder()
                .itemNumber(itemDto.getItemNumber())
                .name(itemDto.getName())
                .state(itemDto.getState())
                .addedDate(itemDto.getAddedDate())
                .updateDate(itemDto.getUpdateDate())
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
}
