package com.scm.backend.web;

import com.scm.backend.model.dto.*;
import com.scm.backend.model.entity.*;
import com.scm.backend.service.*;
import com.scm.backend.util.PermissionDtoMapper;
import com.scm.backend.util.RoleDtoMapper;
import com.scm.backend.util.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionDtoMapper permissionDtoMapper;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Autowired
    private RoleDtoMapper roleDtoMapper;

    @PostMapping("/createRole")
    public ResponseEntity<ResponseDto> createRole(@Valid @RequestBody Role role) {
        roleService.createRole(role);
        ResponseDto responseDto = new ResponseDto("Create role successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/createPermission")
    public ResponseEntity<ResponseDto> createPermission(@Valid @RequestBody Permission permission) {
        permissionService.createPermission(permission);
        ResponseDto responseDto = new ResponseDto("Create permission successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/createUserRole")
    public ResponseEntity<ResponseDto> createUserRole(@Valid @RequestBody UserRoleDto userRoleDto) throws Exception {
        userRoleService.createUserRole(userRoleDto);
        ResponseDto responseDto = new ResponseDto("Create user-role successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/createRolePermission")
    public ResponseEntity<ResponseDto> findByKey(@Valid @RequestBody RolePermissionDto rolePermissionDto) throws Exception {
        rolePermissionService.createRolePermission(rolePermissionDto);
        ResponseDto responseDto = new ResponseDto("Create role-permission successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getUserPermission")
    public ResponseEntity<ResponseDto> getUserPermission(Principal principal) {
        UserInvoiceDto userInvoiceDto = new UserInvoiceDto();
        userInvoiceDto.setUsername(principal.getName());

        List<Permission> permissionList = userRoleService.getUserPermission(userInvoiceDto);
        List<PermissionDto> permissionDtoList = new ArrayList<>();
        for(Permission p : permissionList){
            permissionDtoList.add(permissionDtoMapper.toPermissionDto(p));
        }

        User user = userService.findUserByUsername(principal.getName()).orElseThrow(()
                -> new UsernameNotFoundException("Can not found username: " + principal.getName()));

        UserDto userDto = userDtoMapper.toUserDto(user);

        userDto.setPermissionList(permissionDtoList);
        ResponseDto responseDto = new ResponseDto("successfully", HttpStatus.OK, userDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getAllPermission")
    public ResponseEntity<ResponseDto> getAllPermission() {
        List<Permission> permissionList = permissionService.getAllPermission();

        List<PermissionDto> permissionDtoList = permissionDtoMapper.toPermissionDtoList(permissionList);

        ResponseDto responseDto = new ResponseDto("Get permission list successfully", HttpStatus.OK, permissionDtoList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getAllRole")
    public ResponseEntity<ResponseDto> getAllRole() {
        List<Role> roleList = roleService.getAllRole();

        List<RoleDto> roleDtoList = roleDtoMapper.toRoleDtoList(roleList);

        ResponseDto responseDto = new ResponseDto("Get role list successfully", HttpStatus.OK, roleDtoList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
