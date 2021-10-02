package com.scm.backend.web;

import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.exception.ItemNumberAlreadyExistException;
import com.scm.backend.model.exception.ItemNumberLessThanOne;
import com.scm.backend.service.ItemService;
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
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDtoMapper itemDtoMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> createProject(@Valid @RequestBody ItemDto itemDto) throws ItemNumberLessThanOne, ItemNumberAlreadyExistException {
        itemService.createItem(itemDto);
        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
