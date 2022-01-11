package com.scm.backend.service;

import com.scm.backend.model.dto.UserDto;
import com.scm.backend.model.entity.User;
import com.scm.backend.model.exception.*;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(UserDto userDto) throws UsernameAlreadyExistException, EmailNotExistException, UpdateException;
    Optional<User> findUserByUsername(String username);
    List<User> getAllUser();
    User updateUser(UserDto userDto, String currentUsername) throws UsernameNotExistException, UpdateException, ConcurrentUpdateException, EmailNotExistException;
    void deleteUser(UserDto userDto, String currentUsername) throws DeleteException;
}
