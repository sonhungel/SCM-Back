package com.scm.backend.service;

import com.scm.backend.model.dto.InvoiceDto;
import com.scm.backend.model.entity.Invoice;
import com.scm.backend.model.exception.CustomerNumberNotFoundException;

import java.util.List;

public interface InvoiceService {
    Invoice createInvoice(InvoiceDto invoiceDto) throws CustomerNumberNotFoundException;
    List<Invoice> getAllInvoice();
    Invoice createInvoiceFull(InvoiceDto invoiceDto);
}
