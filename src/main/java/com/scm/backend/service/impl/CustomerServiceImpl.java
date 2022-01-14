package com.scm.backend.service.impl;

import com.scm.backend.model.dto.CustomerDto;
import com.scm.backend.model.entity.Customer;
import com.scm.backend.model.entity.Supplier;
import com.scm.backend.model.entity.User;
import com.scm.backend.model.exception.*;
import com.scm.backend.repository.CustomerRepository;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.CustomerService;
import com.scm.backend.util.CustomerDtoMapper;
import com.scm.backend.util.InternalState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerDtoMapper customerDtoMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Customer createCustomer(CustomerDto customerDto) throws CustomerNumberAlreadyExistException {
        checkBeforeCreate(customerDto);
        return createNewWithDtoData(customerDto);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.getAllActiveCustomer();
    }

    @Override
    public void deleteCustomer(Long customerId) throws DeleteException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()
                -> new DeleteException("Customer not found when delete"));

        customer.setInternalState(InternalState.DELETED);
        customerRepository.saveAndFlush(customer);
    }

    @Override
    public void updateCustomer(CustomerDto customerDto) throws UpdateException, ConcurrentUpdateItemException {
        Customer customer = checkBeforeUpdate(customerDto);

        updateWithDto(customer, customerDto);

    }

    private void updateWithDto(Customer customer, CustomerDto customerDto) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setTaxNumber(customerDto.getTaxNumber());
        customer.setSex(customerDto.getSex());
        customer.setAddress(customerDto.getAddress());
        customer.setProvince(customerDto.getProvince());
        customer.setDistrict(customerDto.getDistrict());
        customer.setWard(customerDto.getWard());
        customer.setRemark(customerDto.getRemark());
        customer.setUpdateDate(LocalDate.now());

        customerRepository.saveAndFlush(customer);
    }

    private Customer checkBeforeUpdate(CustomerDto customerDto) throws UpdateException, ConcurrentUpdateItemException {
        Customer customer = customerRepository.findById(customerDto.getId())
                .orElseThrow(() -> new UpdateException("Customer not found when update"));

        if(!Objects.equals(customer.getVersion(), customerDto.getVersion())){
            throw new ConcurrentUpdateItemException("Cannot update item, version have been changed.", customerDto.getId());
        }

        return customer;
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

        if(customerDto.getCustomerNumber() == null) {
            int max = userRepository.getLatestCustomerNumber();
            customerDto.setCustomerNumber(max + 1);
        }
    }
}
