package com.scm.backend.service.impl;

import com.scm.backend.model.dto.InvoiceDto;
import com.scm.backend.model.entity.Invoice;
import com.scm.backend.model.entity.User;
import com.scm.backend.repository.InvoiceRepository;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice createInvoice(InvoiceDto invoiceDto) {
        checkBeforeCreate(invoiceDto);
        return createNewInvoiceWithDtoData(invoiceDto);
    }

    private Invoice createNewInvoiceWithDtoData(InvoiceDto invoiceDto) throws UsernameNotFoundException {
        final String username = invoiceDto.getUser().getUsername();
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        Invoice invoice = Invoice.builder()
                .user(user)
                .build();

        invoiceRepository.saveAndFlush(invoice);

        return invoice;
    }

    private void checkBeforeCreate(InvoiceDto invoiceDto) {

    }
}
