package com.scm.backend.util;

import com.scm.backend.model.dto.InventoryCheckDto;
import com.scm.backend.model.entity.InventoryCheck;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface InventoryCheckDtoMapper {
    InventoryCheck toInventoryCheck(InventoryCheckDto inventoryCheckDto);

    InventoryCheckDto toInventoryCheckDto(InventoryCheck inventoryCheck);

    List<InventoryCheckDto> toInventoryCheckDtoList(List<InventoryCheck> inventoryCheckList);
}
