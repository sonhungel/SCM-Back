package com.scm.backend.service;

import com.scm.backend.model.dto.SupplierDto;
import com.scm.backend.model.entity.Supplier;
import com.scm.backend.model.exception.DeleteException;
import com.scm.backend.model.exception.SupplierNumberAlreadyExist;

import java.util.List;

public interface SupplierService {
    Supplier createSupplier(SupplierDto supplierDto) throws SupplierNumberAlreadyExist;

    List<Supplier> getAllSupplier();

    void deleteSupplier(Long supplierId) throws DeleteException;
}
