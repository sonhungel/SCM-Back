package com.scm.backend.util;

import com.scm.backend.model.dto.SupplierDto;
import com.scm.backend.model.entity.Supplier;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface SupplierDtoMapper {
    List<SupplierDto> toSupplierDtoList(List<Supplier> supplierList);
    SupplierDto toSupplierDto(Supplier supplier);
    Supplier toSupplier(SupplierDto supplierDto);
}
