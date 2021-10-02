package com.scm.backend.util;

import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.entity.Item;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface ItemDtoMapper {
    Item toItem(ItemDto itemDto);
    ItemDto toItemDto(Item item);
}
