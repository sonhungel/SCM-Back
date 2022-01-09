package com.scm.backend.util;

import com.scm.backend.model.dto.UserDto;
import com.scm.backend.model.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface UserDtoMapper {
    List<UserDto> toListUserDto(List<User> userList);
    User toUser(UserDto userDto);
    UserDto toUserDto(User user);
}
