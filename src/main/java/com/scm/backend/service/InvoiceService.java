package com.scm.backend.service;

import com.scm.backend.model.dto.InvoiceDto;
import com.scm.backend.model.entity.Invoice;
import com.scm.backend.model.exception.CustomerNumberNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InvoiceService {
    Invoice createInvoice(InvoiceDto invoiceDto) throws CustomerNumberNotFoundException;
    Page<Invoice> getAllInvoice(int pageNumber);
    Invoice createInvoiceFull(InvoiceDto invoiceDto);
}
