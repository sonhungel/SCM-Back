package com.scm.backend.service;

import com.scm.backend.model.dto.CustomerDto;
import com.scm.backend.model.dto.SupplierDto;
import com.scm.backend.model.entity.Customer;
import com.scm.backend.model.exception.CustomerNumberAlreadyExistException;
import com.scm.backend.model.exception.DeleteException;
import com.scm.backend.model.exception.SupplierNumberAlreadyExist;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(CustomerDto customerDto) throws CustomerNumberAlreadyExistException;
    List<Customer> getCustomers();

    void deleteCustomer(Long customerId) throws DeleteException;
}
