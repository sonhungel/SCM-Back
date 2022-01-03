package com.scm.backend.service;

import com.scm.backend.model.dto.InvoiceDto;
import com.scm.backend.model.entity.Invoice;

public interface InvoiceService {
    Invoice createInvoice(InvoiceDto invoiceDto);
}
