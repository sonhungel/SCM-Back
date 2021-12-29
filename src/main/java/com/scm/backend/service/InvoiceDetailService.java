package com.scm.backend.service;

import com.scm.backend.model.dto.InvoiceDetailDto;
import com.scm.backend.model.exception.InvoiceNotFoundException;
import com.scm.backend.model.exception.ItemNumberNotFoundException;

public interface InvoiceDetailService {
    void createInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws ItemNumberNotFoundException, InvoiceNotFoundException;
}
