package com.scm.backend.service.impl;

import com.scm.backend.model.dto.CustomerDto;
import com.scm.backend.model.dto.SupplierDto;
import com.scm.backend.model.entity.Customer;
import com.scm.backend.model.entity.Supplier;
import com.scm.backend.model.exception.ConcurrentUpdateItemException;
import com.scm.backend.model.exception.DeleteException;
import com.scm.backend.model.exception.SupplierNumberAlreadyExist;
import com.scm.backend.model.exception.UpdateException;
import com.scm.backend.repository.SupplierRepository;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.SupplierService;
import com.scm.backend.util.InternalState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Supplier createSupplier(SupplierDto supplierDto) throws SupplierNumberAlreadyExist {
        checkBeforeCreate(supplierDto);
        return createNewItemWithDtoData(supplierDto);
    }

    @Override
    public List<Supplier> getAllSupplier() {
        return supplierRepository.getAllActiveSupplier();
    }

    @Override
    public void deleteSupplier(Long supplierId) throws DeleteException {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(()
                -> new DeleteException("Supplier not found when delete"));

        supplier.setInternalState(InternalState.DELETED);
        supplierRepository.saveAndFlush(supplier);

    }

    @Override
    public void updateSupplier(SupplierDto supplierDto) throws ConcurrentUpdateItemException, UpdateException {
        Supplier supplier = checkBeforeUpdate(supplierDto);

        updateWithDto(supplier, supplierDto);
    }

    private void updateWithDto(Supplier supplier, SupplierDto supplierDto) {
        supplier.setName(supplierDto.getName());
        supplier.setEmail(supplierDto.getEmail());
        supplier.setPhoneNumber(supplierDto.getPhoneNumber());
        supplier.setTaxNumber(supplierDto.getTaxNumber());
        supplier.setAddress(supplierDto.getAddress());
        supplier.setProvince(supplierDto.getProvince());
        supplier.setWard(supplierDto.getWard());
        supplier.setDistrict(supplierDto.getDistrict());
        supplier.setRemark(supplierDto.getRemark());

        supplierRepository.saveAndFlush(supplier);
    }

    private Supplier checkBeforeUpdate(SupplierDto supplierDto) throws UpdateException, ConcurrentUpdateItemException {
        Supplier supplier = supplierRepository.findById(supplierDto.getId())
                .orElseThrow(() -> new UpdateException("Supplier not found when update"));

        if(!Objects.equals(supplier.getVersion(), supplierDto.getVersion())){
            throw new ConcurrentUpdateItemException("Cannot update supplier, version have been changed.", supplierDto.getId());
        }

        return supplier;
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

        if(supplierDto.getSupplierNumber() == null) {
            int max = userRepository.getLatestSupplierNumber();
            supplierDto.setSupplierNumber(max + 1);
        }
    }
}
