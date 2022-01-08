package com.scm.backend.web;

import com.scm.backend.model.dto.CustomerDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.dto.SupplierDto;
import com.scm.backend.model.entity.Customer;
import com.scm.backend.model.exception.CustomerNumberAlreadyExistException;
import com.scm.backend.model.exception.SupplierNumberAlreadyExist;
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
    public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) throws CustomerNumberAlreadyExistException {
        customerService.createCustomer(customerDto);
        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getCustomers() {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        List<Customer> customerList = customerService.getCustomers();

        for(Customer c : customerList){
            customerDtoList.add(customerDtoMapper.toCustomerDto(c));
        }

        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, customerDtoList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}