package com.scm.backend.service;

import com.scm.backend.model.dto.InvoiceDto;

public interface InvoiceService {
    void createInvoice(InvoiceDto invoiceDto);
}
