package com.scm.backend.util;

import com.scm.backend.model.dto.ItemTypeDto;
import com.scm.backend.model.entity.ItemType;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface ItemTypeDtoMapper {
    List<ItemTypeDto> toListItemTypeDto(List<ItemType> itemTypeList);
    ItemType toItemType(ItemTypeDto itemTypeDto);
    ItemTypeDto toItemTypeDto(ItemType itemType);
}
