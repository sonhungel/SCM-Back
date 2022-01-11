package com.scm.backend.service.impl;

import com.scm.backend.model.dto.UserInvoiceDto;
import com.scm.backend.model.dto.UserRoleDto;
import com.scm.backend.model.entity.*;
import com.scm.backend.model.exception.UpdateException;
import com.scm.backend.repository.*;
import com.scm.backend.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public void createUserRole(UserRoleDto userRoleDto) throws UpdateException {
        User user = userRepository.findUserById(userRoleDto.getKey().getUser().getId())
                .orElseThrow(() -> new UpdateException("User ID does not exist"));

        Role role = roleRepository.findById(userRoleDto.getKey().getRole().getId())
                .orElseThrow(() -> new UpdateException("Role ID does not exist"));

        UserRoleKey key = UserRoleKey.builder()
                .user(user)
                .role(role)
                .build()
                ;

        UserRole userRole = UserRole.builder()
                .key(key)
                .addedDate(LocalDate.now())
                .build()
                ;
        userRoleRepository.saveAndFlush(userRole);
    }

    @Override
    public void createUserRoleByKeyId(Long userId, Long roleId) throws UpdateException {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UpdateException("User ID does not exist"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new UpdateException("Role ID does not exist"));

        UserRoleKey key = UserRoleKey.builder()
                .user(user)
                .role(role)
                .build()
                ;

        UserRole userRole = UserRole.builder()
                .key(key)
                .addedDate(LocalDate.now())
                .build()
                ;
        userRoleRepository.saveAndFlush(userRole);
    }

    @Override
    public List<Permission> getUserPermission(UserInvoiceDto userInvoiceDto) {
        User user = userRepository.findUserByUsername(userInvoiceDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found"));
        List<UserRole> userRoleList = userRoleRepository.findByKey_User_Id(user.getId());
        if(!userRoleList.isEmpty()){
            List<Permission> permissionList = new ArrayList<>();

            for(UserRole u : userRoleList){
                List<RolePermission> rolePermissionList = rolePermissionRepository.findByKey_Role_Id(u.getKey().getRole().getId());
                List<Long> permissionIds = rolePermissionList.stream().map(e -> e.getKey().getPermission().getId()).collect(Collectors.toList());
                permissionList.addAll(permissionRepository.findAllById(permissionIds));
            }
            return permissionList;
        }
        return null;
    }
}
