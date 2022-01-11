package com.scm.backend.service;

import com.scm.backend.model.dto.UserDto;
import com.scm.backend.model.entity.UserRole;
import com.scm.backend.model.exception.ConcurrentUpdateException;
import com.scm.backend.model.exception.UpdateException;
import com.scm.backend.model.exception.UsernameNotExistException;

import java.util.List;

public interface HelperService {
    void changeUserRole();
    void deleteAllUserRole(UserDto userDto, String currentUsername) throws UsernameNotExistException, UpdateException;
    void addUserRole(UserDto userDto, String currentUsername) throws UsernameNotExistException, UpdateException;
}
