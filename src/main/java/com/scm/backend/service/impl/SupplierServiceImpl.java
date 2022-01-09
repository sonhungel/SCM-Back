package com.scm.backend.service.impl;

import com.scm.backend.model.dto.SupplierDto;
import com.scm.backend.model.entity.Supplier;
import com.scm.backend.model.exception.SupplierNumberAlreadyExist;
import com.scm.backend.repository.SupplierRepository;
import com.scm.backend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public Supplier createSupplier(SupplierDto supplierDto) throws SupplierNumberAlreadyExist {
        checkBeforeCreate(supplierDto);
        return createNewItemWithDtoData(supplierDto);
    }

    @Override
    public List<Supplier> getAllSupplier() {
        return supplierRepository.findAll();
    }

    private Supplier createNewItemWithDtoData(SupplierDto supplierDto) {
        Supplier supplier = createNewSupplier(supplierDto);

        supplierRepository.saveAndFlush(supplier);

        return supplier;
    }

    private Supplier createNewSupplier(SupplierDto supplierDto) {

        return Supplier.builder()
                .supplierNumber(supplierDto.getSupplierNumber())
                .name(supplierDto.getName())
                .email(supplierDto.getEmail())
                .phoneNumber(supplierDto.getPhoneNumber())
                .taxNumber(supplierDto.getTaxNumber())
                .type(supplierDto.getType())
                .address(supplierDto.getAddress())
                .province(supplierDto.getProvince())
                .ward(supplierDto.getWard())
                .district(supplierDto.getDistrict())
                .remark(supplierDto.getRemark())
                .paid(0L)
                .addedDate(LocalDate.now())
                .build()
                ;
    }

    private void checkBeforeCreate(SupplierDto supplierDto) throws SupplierNumberAlreadyExist {
        if (supplierRepository.findSupplierBySupplierNumber(supplierDto.getSupplierNumber()).isPresent()){
            throw new SupplierNumberAlreadyExist("Supplier number already exist.", supplierDto.getSupplierNumber());
        }
    }
}
