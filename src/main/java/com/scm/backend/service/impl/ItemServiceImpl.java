package com.scm.backend.service.impl;

import com.google.common.collect.Sets;
import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.entity.ItemType;
import com.scm.backend.model.entity.Supplier;
import com.scm.backend.model.exception.*;
import com.scm.backend.repository.ItemRepository;
import com.scm.backend.repository.SupplierRepository;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.repository.custom.ItemRepositoryCustom;
import com.scm.backend.service.ItemService;
import com.scm.backend.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemTypeService itemTypeService;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createItem(ItemDto itemDto) throws ItemNumberAlreadyExistException, ItemNumberLessThanOne, ItemTypeNotFoundException, SupplierNotFoundException {
        checkBeforeCreate(itemDto);
        createNewItemWithDtoData(itemDto);
    }

    @Override
    public Item updateItem(ItemDto itemDto) throws ItemNumberNotFoundException, ConcurrentUpdateItemException, ItemTypeNotFoundException {
        final Item item = checkBeforeUpdate(itemDto);
        return updateItemWithDtoData(item, itemDto);
    }

    @Override
    public Item getItemByItemNumber(Integer itemNumber) throws ItemNumberNotFoundException {

        return itemRepository.findItemByItemNumber(itemNumber).orElseThrow(()
                -> new ItemNumberNotFoundException("Item number not found.", itemNumber));
    }

    @Override
    public List<Item> findItemWithQuery(String searchString) {
        return itemRepository.findItemsQuery(searchString);
    }

    @Override
    public void deleteItems(List<Integer> itemNumbers) {
        itemRepository.deleteItems(itemNumbers);
    }

    private Item updateItemWithDtoData(Item item, ItemDto itemDto) {

        item.setName(itemDto.getName());
        //item.setState(itemDto.getState());
        item.setUpdateDate(LocalDate.now());
        //item.setQuantity(itemDto.getQuantity());
        item.setSalesPrice(itemDto.getSalesPrice());
        item.setCost(itemDto.getCost());
        //item.setItemType(itemType);
        item.setMinimumQuantity(itemDto.getMinimumQuantity());
        item.setDescription(itemDto.getDescription());
        item.setRemark(itemDto.getRemark());

        itemRepository.save(item);

        return item;
    }

    private void createNewItemWithDtoData(ItemDto itemDto) throws ItemTypeNotFoundException, SupplierNotFoundException {
        final Item item = createNewItem(itemDto);

        itemRepository.saveAndFlush(item);
    }

    private Item createNewItem(ItemDto itemDto) throws ItemTypeNotFoundException, SupplierNotFoundException {
        final ItemType itemType = itemTypeService.findItemTypeById(itemDto.getItemType().getId())
                .orElseThrow(() -> new ItemTypeNotFoundException("Item type not found.", itemDto.getItemType().getTypeName()));

        final Supplier supplier = supplierRepository.findSupplierBySupplierNumber(itemDto.getSupplier().getSupplierNumber())
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found", itemDto.getSupplier().getSupplierNumber()));

        if(supplier.getPaid() == null){
            supplier.setPaid(0L);
        }
        supplier.setLatestSupply(LocalDate.now());
        supplier.setPaid(supplier.getPaid() + (itemDto.getCost()) * itemDto.getQuantity());
        supplierRepository.saveAndFlush(supplier);

        itemDto.setAvailableQuantity(itemDto.getQuantity());

        return Item.builder()
                .itemNumber(itemDto.getItemNumber())
                .name(itemDto.getName())
                .state(itemDto.getState())
                .addedDate(itemDto.getAddedDate())
                .updateDate(itemDto.getUpdateDate())
                .quantity(itemDto.getQuantity())
                .availableQuantity(itemDto.getAvailableQuantity())
                .minimumQuantity(itemDto.getMinimumQuantity())
                .salesPrice(itemDto.getSalesPrice())
                .cost(itemDto.getCost())
                .itemType(itemType)
                .supplier(supplier)
                .description(itemDto.getDescription())
                .remark(itemDto.getRemark())
                .addedDate(LocalDate.now())
                .build()
                ;
    }

    private void checkBeforeCreate(ItemDto itemDto) throws ItemNumberAlreadyExistException, ItemNumberLessThanOne {
        if (itemRepository.findItemByItemNumber(itemDto.getItemNumber()).isPresent()) {
            throw new ItemNumberAlreadyExistException("Item number already exist", itemDto.getItemNumber());
        }

        if(itemDto.getItemNumber() == null) {
            int max = userRepository.getLatestItemId();
            itemDto.setItemNumber(max + 1);
        }

        if(itemDto.getItemNumber() < 1){
            throw new ItemNumberLessThanOne("Item number must be larger than 0", itemDto.getItemNumber());
        }
    }

    private Item checkBeforeUpdate(ItemDto itemDto) throws ItemNumberNotFoundException, ConcurrentUpdateItemException {
        final Item item = itemRepository.findItemByItemNumber(itemDto.getItemNumber())
                .orElseThrow(()-> new ItemNumberNotFoundException("Item number not found.", itemDto.getItemNumber()));

        if(!Objects.equals(item.getVersion(), itemDto.getVersion())){
            throw new ConcurrentUpdateItemException("Cannot update item, version have been changed.", itemDto.getId());
        }

        return item;

    }
}
