package com.scm.backend.web;

import com.scm.backend.model.dto.ItemTypeDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.service.ItemTypeService;
import com.scm.backend.util.ItemDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/itemType")
public class ItemTypeController {
    @Autowired
    private ItemTypeService itemTypeService;

    @Autowired
    private ItemDtoMapper itemDtoMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> createItem(@Valid @RequestBody ItemTypeDto itemTypeDto) {
        itemTypeService.createItemType(itemTypeDto);
        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
