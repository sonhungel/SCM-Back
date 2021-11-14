package com.scm.backend.util;

import com.scm.backend.model.dto.ItemTypeDto;
import com.scm.backend.model.entity.ItemType;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface ItemTypeDtoMapper {
    ItemType toItemType(ItemTypeDto itemTypeDto);
    ItemTypeDto toItemTypeDto(ItemType itemType);
}
