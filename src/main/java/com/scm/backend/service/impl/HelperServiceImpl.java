package com.scm.backend.service.impl;

import com.scm.backend.model.dto.UserDto;
import com.scm.backend.model.entity.Role;
import com.scm.backend.model.entity.User;
import com.scm.backend.model.entity.UserRole;
import com.scm.backend.model.entity.UserRoleKey;
import com.scm.backend.model.exception.ConcurrentUpdateException;
import com.scm.backend.model.exception.UpdateException;
import com.scm.backend.model.exception.UsernameNotExistException;
import com.scm.backend.repository.RoleRepository;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.repository.UserRoleRepository;
import com.scm.backend.service.HelperService;
import com.scm.backend.service.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class HelperServiceImpl implements HelperService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleService userRoleService;


    @Override
    public void changeUserRole() {

    }

    @Override
    public void deleteAllUserRole(UserDto userDto, String currentUsername) throws UsernameNotExistException, UpdateException {
        User user = userRepository.findUserByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotExistException("Username not exist while update info", userDto.getUsername()));

        User currentUser = userRepository.findUserByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotExistException("Current username not exist while update info", currentUsername));

        if(!currentUser.getUserRoleList().isEmpty()){
            if(!StringUtils.equals(currentUser.getUserRoleList().get(0).getKey().getRole().getName(), "Quản lý")) { // not manager
                if(!Objects.equals(user.getUsername(), currentUsername)){
                    throw new UpdateException("Update failed, only manager can update another user");
                }
            } else { // is manager
                if(!user.getUserRoleList().isEmpty()) {
                    if(!StringUtils.equals(user.getUserRoleList().get(0).getKey().getRole().getName(), userDto.getRole())){
                        // xoa role cu, tao role moi
                        List<UserRole> userRoleList = userRoleRepository.findByKey_User_Id(user.getId());
                        for(UserRole u : userRoleList){
                            userRoleRepository.deleteByUserIdRoleId(user.getId());
                        }
                        /*List<Role> roleList = roleRepository.findByName(userDto.getRole());
                        if(roleList.isEmpty()) {
                            throw new UpdateException("Role name not found when update role for user");
                        }
                        Role role = roleList.get(0);

                        userRoleService.createUserRoleByKeyId(user.getId(), role.getId());*/
                    }
                }
            }
        }
    }

    @Override
    public void addUserRole(UserDto userDto, String currentUsername) throws UsernameNotExistException, UpdateException {
        User user = userRepository.findUserByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotExistException("Username not exist while update info", userDto.getUsername()));

        User currentUser = userRepository.findUserByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotExistException("Current username not exist while update info", currentUsername));

        if(!currentUser.getUserRoleList().isEmpty()){
            if(!StringUtils.equals(currentUser.getUserRoleList().get(0).getKey().getRole().getName(), "Quản lý")) { // not manager
                if(!Objects.equals(user.getUsername(), currentUsername)){
                    throw new UpdateException("Update failed, only manager can update another user");
                }
            } else { // is manager
                if(!user.getUserRoleList().isEmpty()) {
                    if(!StringUtils.equals(user.getUserRoleList().get(0).getKey().getRole().getName(), userDto.getRole())){

                        List<Role> roleList = roleRepository.findByName(userDto.getRole());
                        if(roleList.isEmpty()) {
                            throw new UpdateException("Role name not found when update role for user");
                        }
                        Role role = roleList.get(0);

                        userRoleService.createUserRoleByKeyId(user.getId(), role.getId());
                    }
                }
            }
        }
    }
}
