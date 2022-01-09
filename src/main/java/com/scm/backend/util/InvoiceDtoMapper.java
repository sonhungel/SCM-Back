package com.scm.backend.util;

import com.scm.backend.model.dto.InvoiceDto;
import com.scm.backend.model.entity.Invoice;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface InvoiceDtoMapper {
    List<InvoiceDto> toInvoiceDtoList(List<Invoice> invoiceList);
    Invoice toInvoice(InvoiceDto invoiceDto);
    InvoiceDto toInvoiceDto(Invoice invoice);
}
