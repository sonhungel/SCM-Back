package com.scm.backend.service.impl;

import com.scm.backend.model.dto.CustomerDto;
import com.scm.backend.model.entity.Customer;
import com.scm.backend.model.entity.Supplier;
import com.scm.backend.model.exception.CustomerNumberAlreadyExistException;
import com.scm.backend.model.exception.SupplierNumberAlreadyExist;
import com.scm.backend.repository.CustomerRepository;
import com.scm.backend.service.CustomerService;
import com.scm.backend.util.CustomerDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerDtoMapper customerDtoMapper;

    @Override
    public Customer createCustomer(CustomerDto customerDto) throws CustomerNumberAlreadyExistException {
        checkBeforeCreate(customerDto);
        return createNewWithDtoData(customerDto);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    private Customer createNewWithDtoData(CustomerDto customerDto) {
        Customer customer = createNewCustomer(customerDto);

        customerRepository.saveAndFlush(customer);
        return customer;
    }

    private Customer createNewCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .customerNumber(customerDto.getCustomerNumber())
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .phoneNumber(customerDto.getPhoneNumber())
                .dateOfBirth(customerDto.getDateOfBirth())
                .taxNumber(customerDto.getTaxNumber())
                .sex(customerDto.getSex())
                .address(customerDto.getAddress())
                .province(customerDto.getProvince())
                .ward(customerDto.getWard())
                .district(customerDto.getDistrict())
                .remark(customerDto.getRemark())
                .paid(0L)
                .addedDate(LocalDate.now())
                .build()
                ;
    }

    private void checkBeforeCreate(CustomerDto customerDto) throws CustomerNumberAlreadyExistException {
        if (customerRepository.findCustomerByCustomerNumber(customerDto.getCustomerNumber()).isPresent()){
            throw new CustomerNumberAlreadyExistException("Customer number already exist.", customerDto.getCustomerNumber());
        }
    }
}
