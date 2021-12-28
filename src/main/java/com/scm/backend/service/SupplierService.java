package com.scm.backend.service;

import com.scm.backend.model.dto.SupplierDto;
import com.scm.backend.model.exception.SupplierNumberAlreadyExist;

public interface SupplierService {
    void createSupplier(SupplierDto supplierDto) throws SupplierNumberAlreadyExist;
}
