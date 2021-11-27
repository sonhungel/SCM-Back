package com.scm.backend.service;

import com.scm.backend.model.dto.UserDto;
import com.scm.backend.model.entity.User;
import com.scm.backend.model.exception.UsernameAlreadyExistException;

import java.util.Optional;

public interface UserService {
    void saveUser(UserDto userDto) throws UsernameAlreadyExistException;
    Optional<User> findUserByUsername(String username);

}
