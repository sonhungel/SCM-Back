package com.scm.backend.web;

import com.scm.backend.model.dto.CustomerDto;
import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.dto.SupplierDto;
import com.scm.backend.model.entity.Customer;
import com.scm.backend.model.exception.*;
import com.scm.backend.service.CustomerService;
import com.scm.backend.service.SupplierService;
import com.scm.backend.util.CustomerDtoMapper;
import com.scm.backend.util.SupplierDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDtoMapper customerDtoMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody CustomerDto customerDto)
            throws CustomerNumberAlreadyExistException {
        Customer customer = customerService.createCustomer(customerDto);
        CustomerDto customerDto1 = customerDtoMapper.toCustomerDto(customer);

        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, customerDto1);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto) throws ConcurrentUpdateItemException, UpdateException {
        customerService.updateCustomer(customerDto);
        ResponseDto responseDto = new ResponseDto("Update successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getAllCustomer")
    public ResponseEntity<ResponseDto> getAllCustomer() {
        List<Customer> customerList = customerService.getCustomers();

        List<CustomerDto> customerDtoList = customerDtoMapper.toCustomerDtoList(customerList);

        ResponseDto responseDto = new ResponseDto("Get successfully", HttpStatus.OK, customerDtoList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<ResponseDto> deleteCustomer(@PathVariable("customerId") Long customerId) throws DeleteException {
        customerService.deleteCustomer(customerId);

        ResponseDto responseDto = new ResponseDto("Update successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
