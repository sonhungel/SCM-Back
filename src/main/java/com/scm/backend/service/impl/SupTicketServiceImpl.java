package com.scm.backend.service.impl;

import com.scm.backend.model.dto.SuptTicketDto;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.entity.SupTicket;
import com.scm.backend.model.entity.Supplier;
import com.scm.backend.model.exception.CreateException;
import com.scm.backend.repository.ItemRepository;
import com.scm.backend.repository.SupTicketRepository;
import com.scm.backend.repository.SupplierRepository;
import com.scm.backend.service.SupTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(rollbackFor = Exception.class)
public class SupTicketServiceImpl implements SupTicketService {
    @Autowired
    private SupTicketRepository supTicketRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public SupTicket createSupTicket(SuptTicketDto suptTicketDto) throws CreateException {
        checkBeforeCreate(suptTicketDto);

        SupTicket supTicket = createNewSupTK(suptTicketDto);

        supTicketRepository.saveAndFlush(supTicket);

        return supTicket;
    }

    private SupTicket createNewSupTK(SuptTicketDto suptTicketDto) throws CreateException {
        Item item = itemRepository.findById(suptTicketDto.getItem().getId()).orElseThrow(()
                -> new CreateException("Item not found while create sup ticket"));

        Supplier supplier = supplierRepository.findById(suptTicketDto.getSupplier().getId())
                .orElseThrow(() -> new CreateException("Supplier not found while create sup ticket"));

        item.setQuantity(item.getQuantity() + suptTicketDto.getQuantity());
        item.setAvailableQuantity(item.getAvailableQuantity() + suptTicketDto.getQuantity());
        item.setUpdateDate(LocalDate.now());
        itemRepository.saveAndFlush(item);

        supplier.setLatestSupply(LocalDate.now());
        supplier.setUpdateDate(LocalDate.now());
        supplierRepository.saveAndFlush(supplier);

        return SupTicket.builder()
                .item(item)
                .supplier(supplier)
                .cost(suptTicketDto.getCost())
                .quantity(suptTicketDto.getQuantity())
                .addedDate(LocalDate.now())
                .build()
                ;
    }

    private void checkBeforeCreate(SuptTicketDto suptTicketDto) {
    }
}
