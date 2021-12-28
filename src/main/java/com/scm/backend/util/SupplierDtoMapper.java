package com.scm.backend.util;

import com.scm.backend.model.dto.SupplierDto;
import com.scm.backend.model.entity.Supplier;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface SupplierDtoMapper {
    SupplierDto toSupplierDto(Supplier supplier);
    Supplier toSupplier(SupplierDto supplierDto);
}
