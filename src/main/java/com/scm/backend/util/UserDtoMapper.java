package com.scm.backend.util;

import com.scm.backend.model.dto.UserDto;
import com.scm.backend.model.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface UserDtoMapper {
    User toUser(UserDto userDto);
    UserDto toUserDto(User user);
}
