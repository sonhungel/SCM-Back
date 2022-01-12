package com.scm.backend.service.impl;

import com.scm.backend.model.dto.InventoryCheckDto;
import com.scm.backend.model.entity.InventoryCheck;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.entity.User;
import com.scm.backend.model.exception.ItemNumberNotFoundException;
import com.scm.backend.model.exception.UpdateException;
import com.scm.backend.repository.InventoryCheckRepository;
import com.scm.backend.repository.ItemRepository;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.InventoryCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class InventoryCheckServiceImpl implements InventoryCheckService {
    @Autowired
    private InventoryCheckRepository inventoryCheckRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public InventoryCheck createInventoryCheck(InventoryCheckDto inventoryCheckDto) throws ItemNumberNotFoundException, UpdateException {
        checkBeforeCreate(inventoryCheckDto);
        InventoryCheck inventoryCheck = createNew(inventoryCheckDto);
        inventoryCheckRepository.saveAndFlush(inventoryCheck);
        return inventoryCheck;
    }

    @Override
    public List<InventoryCheck> getInventoryChecks() {
        return inventoryCheckRepository.findAll();
    }

    private InventoryCheck createNew(InventoryCheckDto inventoryCheckDto) throws ItemNumberNotFoundException, UpdateException {
        Item item = itemRepository.findItemByItemNumber(inventoryCheckDto.getItem().getItemNumber())
                .orElseThrow(()
                        -> new ItemNumberNotFoundException(
                                "Item number not found while create new inventory check",
                        inventoryCheckDto.getItem().getItemNumber()));

        User user = userRepository.findUserByUsername(inventoryCheckDto.getUser().getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found while create Inventory check"));

        item.setAvailableQuantity(inventoryCheckDto.getAvailableQuantity());

        if(item.getAvailableQuantity() > item.getQuantity()){
            throw new UpdateException("Available quantity can not larger than quantity when update inventory check");
        }

        itemRepository.save(item);

        return InventoryCheck.builder()
                .user(user)
                .item(item)
                .availableQuantity(inventoryCheckDto.getAvailableQuantity())
                .remark(inventoryCheckDto.getRemark())
                .addedDate(LocalDate.now())
                .build()
                ;
    }

    private void checkBeforeCreate(InventoryCheckDto inventoryCheckDto) {
    }
}
