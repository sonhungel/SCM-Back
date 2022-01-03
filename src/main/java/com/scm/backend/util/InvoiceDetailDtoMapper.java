package com.scm.backend.util;

import com.scm.backend.model.dto.InvoiceDetailDto;
import com.scm.backend.model.entity.InvoiceDetail;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface InvoiceDetailDtoMapper {
    InvoiceDetail toInvoiceDetail(InvoiceDetailDto invoiceDetailDto);
    InvoiceDetailDto toInvoiceDetailDto(InvoiceDetail invoiceDetail);
}
