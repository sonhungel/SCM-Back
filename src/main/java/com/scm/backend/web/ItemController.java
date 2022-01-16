package com.scm.backend.web;

import com.scm.backend.model.dto.GetItemsDto;
import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.exception.*;
import com.scm.backend.service.ItemService;
import com.scm.backend.util.InternalState;
import com.scm.backend.util.ItemDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemDtoMapper itemDtoMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> createItem(@Valid @RequestBody ItemDto itemDto)
            throws ItemNumberLessThanOne, ItemNumberAlreadyExistException, ItemTypeNotFoundException, SupplierNotFoundException, CreateException {
        itemService.createItem(itemDto);
        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateItem(@Valid @RequestBody ItemDto itemDto)
            throws ItemNumberNotFoundException, ConcurrentUpdateItemException, ItemTypeNotFoundException {
        itemService.updateItem(itemDto);
        ResponseDto responseDto = new ResponseDto("Update successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getItems/{itemNumber}")
    public ResponseEntity<ResponseDto> getItemByItemNumber(@PathVariable Integer itemNumber)
            throws ItemNumberNotFoundException {
        Item item = itemService.getItemByItemNumber(itemNumber);
        ItemDto result = itemDtoMapper.toItemDto(item);

        ResponseDto responseDto = new ResponseDto("Update successfully", HttpStatus.OK, result);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(params = {"searchValue"})
    public List<ItemDto> finItems(@RequestParam("searchValue") String searchValue){
        return itemDtoMapper.toListItemDto(itemService.findItemWithQuery(searchValue)
                .stream().filter(e -> e.getInternalState() != InternalState.DELETED).collect(Collectors.toList()));
    }

    /*@GetMapping("/addQuantity")
    public List<ItemDto> finItems(@RequestParam("searchValue") String searchValue){
        return itemDtoMapper.toListItemDto(itemService.findItemWithQuery(searchValue)
                .stream().filter(e -> e.getInternalState() != InternalState.DELETED).collect(Collectors.toList()));
    }*/

    @GetMapping("/delete")
    public ResponseEntity<ResponseDto> deleteItem(@Valid @RequestBody List<Integer> itemNumbers){
        itemService.deleteItems(itemNumbers);
        ResponseDto responseDto = new ResponseDto("Delete successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{itemNumber}")
    public ResponseEntity<ResponseDto> deleteItem(@PathVariable("itemNumber") Integer itemNumber){
        List<Integer> itemNumbers = new ArrayList<>();
        itemNumbers.add(itemNumber);
        itemService.deleteItems(itemNumbers);
        ResponseDto responseDto = new ResponseDto("Delete successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
