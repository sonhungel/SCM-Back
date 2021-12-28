package com.scm.backend.service.impl;

import com.scm.backend.model.dto.SupplierDto;
import com.scm.backend.model.entity.Supplier;
import com.scm.backend.model.exception.SupplierNumberAlreadyExist;
import com.scm.backend.repository.SupplierRepository;
import com.scm.backend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public void createSupplier(SupplierDto supplierDto) throws SupplierNumberAlreadyExist {
        checkBeforeCreate(supplierDto);
        createNewItemWithDtoData(supplierDto);
    }

    private void createNewItemWithDtoData(SupplierDto supplierDto) {
        Supplier supplier = createNewSupplier(supplierDto);

        supplierRepository.saveAndFlush(supplier);
    }

    private Supplier createNewSupplier(SupplierDto supplierDto) {

        return Supplier.builder()
                .supplierNumber(supplierDto.getSupplierNumber())
                .name(supplierDto.getName())
                .build()
                ;
    }

    private void checkBeforeCreate(SupplierDto supplierDto) throws SupplierNumberAlreadyExist {
        if (supplierRepository.findSupplierBySupplierNumber(supplierDto.getSupplierNumber()).isPresent()){
            throw new SupplierNumberAlreadyExist("Supplier number already exist.", supplierDto.getSupplierNumber());
        }
    }
}
