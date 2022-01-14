package com.scm.backend.web;

import com.scm.backend.model.dto.*;
import com.scm.backend.model.entity.Customer;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.entity.Supplier;
import com.scm.backend.model.exception.DeleteException;
import com.scm.backend.model.exception.ItemNumberNotFoundException;
import com.scm.backend.model.exception.SupplierNumberAlreadyExist;
import com.scm.backend.service.SupplierService;
import com.scm.backend.util.InternalState;
import com.scm.backend.util.SupplierDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierDtoMapper supplierDtoMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> createItem(@Valid @RequestBody SupplierDto supplierDto)
            throws SupplierNumberAlreadyExist {
        Supplier supplier = supplierService.createSupplier(supplierDto);

        SupplierDto result = supplierDtoMapper.toSupplierDto(supplier);

        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, result);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getAllSupplier")
    public ResponseEntity<ResponseDto> getAllSupplier() {
        List<Supplier> supplierList = supplierService.getAllSupplier();

        List<SupplierDto> supplierDtoList = supplierDtoMapper.toSupplierDtoList(supplierList);

        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, supplierDtoList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/delete/{supplierId}")
    public ResponseEntity<ResponseDto> getUserByUsername(@PathVariable("supplierId") Long supplierId) throws DeleteException {
        supplierService.deleteSupplier(supplierId);

        ResponseDto responseDto = new ResponseDto("Update successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
