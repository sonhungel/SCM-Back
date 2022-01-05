package com.scm.backend.web;

import com.scm.backend.model.dto.GetItemsDto;
import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.exception.*;
import com.scm.backend.service.ItemService;
import com.scm.backend.util.ItemDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDtoMapper itemDtoMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> createItem(@Valid @RequestBody ItemDto itemDto) throws ItemNumberLessThanOne, ItemNumberAlreadyExistException, ItemTypeNotFoundException, SupplierNotFoundException {
        itemService.createItem(itemDto);
        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateItem(@Valid @RequestBody ItemDto itemDto) throws ItemNumberNotFoundException, ConcurrentUpdateItemException, ItemTypeNotFoundException {
        itemService.updateItem(itemDto);
        ResponseDto responseDto = new ResponseDto("Update successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getItems")
    public ResponseEntity<ResponseDto> getItemByItemNumber(@Valid @RequestBody GetItemsDto getItemsDto) throws ItemNumberNotFoundException, ConcurrentUpdateItemException, ItemTypeNotFoundException {
        List<Item> itemList = itemService.getItemByItemNumber(getItemsDto.getItemNumber());
        List<ItemDto> result = new ArrayList<>();

        for(Item i : itemList){
            result.add(itemDtoMapper.toItemDto(i));
        }

        ResponseDto responseDto = new ResponseDto("Update successfully", HttpStatus.OK, result);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(params = {"searchValue"})
    public List<ItemDto> finItems(@RequestParam("searchValue") String searchValue){
        return itemDtoMapper.toListItemDto(itemService.findItemWithQuery(searchValue));
    }

}
