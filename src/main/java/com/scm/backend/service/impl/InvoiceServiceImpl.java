package com.scm.backend.service.impl;

import com.scm.backend.model.dto.InvoiceDto;
import com.scm.backend.model.entity.Customer;
import com.scm.backend.model.entity.Invoice;
import com.scm.backend.model.entity.User;
import com.scm.backend.model.exception.CustomerNumberNotFoundException;
import com.scm.backend.repository.CustomerRepository;
import com.scm.backend.repository.InvoiceRepository;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.InvoiceService;
import com.scm.backend.util.InternalState;
import com.scm.backend.util.InvoiceState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Invoice createInvoice(InvoiceDto invoiceDto) throws CustomerNumberNotFoundException {
        checkBeforeCreate(invoiceDto);
        return createNewInvoiceWithDtoData(invoiceDto);
    }

    @Override
    public List<Invoice> getAllInvoice() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice createInvoiceFull(InvoiceDto invoiceDto) {

        return null;
    }

    private Invoice createNewInvoiceWithDtoData(InvoiceDto invoiceDto) throws UsernameNotFoundException, CustomerNumberNotFoundException {
        final String username = invoiceDto.getUser().getUsername();

        User user = userRepository.findUserByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("Username not found"));

        if(invoiceDto.getCustomer().getCustomerNumber() == null){
            throw new CustomerNumberNotFoundException("Customer number could not be NULL",
                    invoiceDto.getCustomer().getCustomerNumber());
        }

        Customer customer = customerRepository.findCustomerByCustomerNumber(invoiceDto.getCustomer().getCustomerNumber())
                .orElseThrow(() -> new CustomerNumberNotFoundException("Customer not found", invoiceDto.getCustomer().getCustomerNumber()));


        LocalDate date = LocalDate.of(2021, 12, 21);
        Invoice invoice = Invoice.builder()
                .user(user)
                .customer(customer)
                .paid(0L)
                .status(InvoiceState.OPEN)
                .internalState(InternalState.ACTIVE)
                //.addedDate(LocalDate.now())
                .addedDate(date)
                .build();

        invoiceRepository.saveAndFlush(invoice);

        return invoice;
    }

    private void checkBeforeCreate(InvoiceDto invoiceDto) {

    }
}
