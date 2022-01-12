package com.scm.backend.web;

import com.scm.backend.model.dto.CustomerDto;
import com.scm.backend.model.dto.InventoryCheckDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.dto.UserInvoiceDto;
import com.scm.backend.model.entity.Customer;
import com.scm.backend.model.entity.InventoryCheck;
import com.scm.backend.model.exception.CustomerNumberAlreadyExistException;
import com.scm.backend.model.exception.ItemNumberNotFoundException;
import com.scm.backend.model.exception.UpdateException;
import com.scm.backend.service.InventoryCheckService;
import com.scm.backend.util.InventoryCheckDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/inventoryCheck")
public class InventoryCheckController {

    @Autowired
    private InventoryCheckService inventoryCheckService;

    @Autowired
    private InventoryCheckDtoMapper inventoryCheckDtoMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> createInventoryCheck(@Valid @RequestBody InventoryCheckDto inventoryCheckDto,
                                                            Principal principal)
            throws ItemNumberNotFoundException, UpdateException {
        UserInvoiceDto userInvoiceDto = new UserInvoiceDto();
        userInvoiceDto.setUsername(principal.getName());

        inventoryCheckDto.setUser(userInvoiceDto);

        InventoryCheck inventoryCheck = inventoryCheckService.createInventoryCheck(inventoryCheckDto);
        InventoryCheckDto result = inventoryCheckDtoMapper.toInventoryCheckDto(inventoryCheck);
        ResponseDto responseDto = new ResponseDto("Create inventory check successfully", HttpStatus.OK, result);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getAllInventoryCheck")
    public ResponseEntity<ResponseDto> getAllInventoryCheck() {
        List<InventoryCheck> inventoryCheckList = inventoryCheckService.getInventoryChecks();

        List<InventoryCheckDto> inventoryCheckDtoList = inventoryCheckDtoMapper.toInventoryCheckDtoList(inventoryCheckList);

        ResponseDto responseDto = new ResponseDto("Get successfully", HttpStatus.OK, inventoryCheckDtoList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
