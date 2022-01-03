package com.scm.backend.web;

import com.scm.backend.model.dto.*;
import com.scm.backend.model.entity.*;
import com.scm.backend.service.PermissionService;
import com.scm.backend.service.RolePermissionService;
import com.scm.backend.service.RoleService;
import com.scm.backend.service.UserRoleService;
import com.scm.backend.util.PermissionDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionDtoMapper permissionDtoMapper;

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
    public ResponseEntity<ResponseDto> getUserPermission(Principal principal) throws Exception {
        UserInvoiceDto userInvoiceDto = new UserInvoiceDto();
        userInvoiceDto.setUsername(principal.getName());

        List<Permission> permissionList = userRoleService.getUserPermission(userInvoiceDto);

        List<PermissionDto> result = new ArrayList<>();
        for(Permission p : permissionList){
            result.add(permissionDtoMapper.toPermissionDto(p));
        }

        ResponseDto responseDto = new ResponseDto("successfully", HttpStatus.OK, result);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
