package com.scm.backend.util;

import com.scm.backend.model.dto.InvoiceDto;
import com.scm.backend.model.entity.Invoice;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface InvoiceDtoMapper {
    Invoice toInvoice(InvoiceDto invoiceDto);
    InvoiceDto toInvoiceDto(Invoice invoice);
}
