package com.scm.backend.web;

import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.dto.ItemTypeDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.entity.ItemType;
import com.scm.backend.model.exception.ConcurrentUpdateException;
import com.scm.backend.model.exception.ConcurrentUpdateItemException;
import com.scm.backend.model.exception.ItemNumberNotFoundException;
import com.scm.backend.model.exception.ItemTypeNotFoundException;
import com.scm.backend.service.ItemTypeService;
import com.scm.backend.util.ItemDtoMapper;
import com.scm.backend.util.ItemTypeDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/itemType")
public class ItemTypeController {
    @Autowired
    private ItemTypeService itemTypeService;

    @Autowired
    private ItemTypeDtoMapper itemTypeDtoMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> createItemType(@Valid @RequestBody ItemTypeDto itemTypeDto) throws ConcurrentUpdateException, ItemTypeNotFoundException {
        itemTypeService.createItemType(itemTypeDto);
        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getNewId")
    public ResponseEntity<ResponseDto> getItemTypeId() {
        ItemType itemType = itemTypeService.getNewItemTypeId();

        ItemTypeDto itemTypeDto = itemTypeDtoMapper.toItemTypeDto(itemType);

        ResponseDto responseDto = new ResponseDto("Get successfully", HttpStatus.OK, itemTypeDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getActiveItemType")
    public ResponseEntity<ResponseDto> getActiveItemTypes() {
        List<ItemType> itemTypes = itemTypeService.getAllActiveItemType();

        List<ItemTypeDto> itemTypeDtoList = itemTypeDtoMapper.toListItemTypeDto(itemTypes);

        ResponseDto responseDto = new ResponseDto("Get successfully", HttpStatus.OK, itemTypeDtoList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
