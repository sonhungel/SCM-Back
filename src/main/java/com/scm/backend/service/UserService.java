package com.scm.backend.service;

import com.scm.backend.model.dto.UserDto;
import com.scm.backend.model.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);
}
