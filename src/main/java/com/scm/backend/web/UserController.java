package com.scm.backend.web;

import com.google.common.collect.Sets;
import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.dto.UserDto;
import com.scm.backend.model.dto.UserInvoiceDto;
import com.scm.backend.model.entity.Permission;
import com.scm.backend.model.entity.User;
import com.scm.backend.model.exception.*;
import com.scm.backend.payload.JWTLoginSuccessResponse;
import com.scm.backend.payload.LoginRequest;
import com.scm.backend.security.JwtTokenProvider;
import com.scm.backend.service.HelperService;
import com.scm.backend.service.MapValidationErrorService;
import com.scm.backend.service.UserRoleService;
import com.scm.backend.service.UserService;
import com.scm.backend.util.InternalState;
import com.scm.backend.util.RoleDtoMapper;
import com.scm.backend.util.UserDtoMapper;
import com.scm.backend.validator.UserDtoValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.scm.backend.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Autowired
    private RoleDtoMapper roleDtoMapper;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserDtoValidator userValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private HelperService helperService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@Valid @RequestBody UserDto userDto, BindingResult result)
            throws UsernameAlreadyExistException, EmailNotExistException, UpdateException {
        userValidator.validate(userDto, result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) {
            ResponseDto responseDto = new ResponseDto("Create failed", HttpStatus.BAD_REQUEST, errorMap);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        User user = userService.saveUser(userDto);

        ResponseDto responseDto = new ResponseDto("Create successfully",
                HttpStatus.CREATED, userDtoMapper.toUserDto(user));
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseDto> getUserByUsername(@PathVariable("username") String username)
            throws UsernameNotFoundException{
        User user = userService.findUserByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("Can not found username: " + username));
        UserDto userDto = userDtoMapper.toUserDto(user);

        UserInvoiceDto userInvoiceDto = new UserInvoiceDto();
        userInvoiceDto.setUsername(username);

        List<Permission> permissionList = userRoleService.getUserPermission(userInvoiceDto);

        if(StringUtils.isNotBlank(permissionList.get(0).getName())){
            userDto.setRole(permissionList.get(0).getName());
        } else {
            userDto.setRole("Not have role");
        }

        ResponseDto responseDto = new ResponseDto("Get user successfully",
                HttpStatus.CREATED, userDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<ResponseDto> getAllUser() {
        List<User> userList = userService.getAllUser().stream().filter(e ->
                e.getInternalState() != InternalState.DELETED).collect(Collectors.toList());
        List<UserDto> userDtoList = userDtoMapper.toListUserDto(userList);
        for(UserDto e : userDtoList){
            if(!e.getUserRoleList().isEmpty() &&
                    StringUtils.isNotBlank(e.getUserRoleList().get(0).getKey().getRole().getName())){
                e.setRole(e.getUserRoleList().get(0).getKey().getRole().getName());
            } else {
                e.setRole("Not have role");
            }
        }
        ResponseDto responseDto = new ResponseDto("Get user successfully",
                HttpStatus.CREATED, userDtoList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateUser(@Valid @RequestBody UserDto userDto, Principal principal)
            throws UsernameNotExistException, UpdateException, ConcurrentUpdateException, EmailNotExistException {
        String currentUsername = principal.getName();

        userService.updateUser(userDto, currentUsername);

        ResponseDto responseDto = new ResponseDto("Update successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/update/admin")
    public ResponseEntity<ResponseDto> updateUserByADMIN(@Valid @RequestBody UserDto userDto, Principal principal)
            throws UsernameNotExistException, UpdateException, ConcurrentUpdateException, EmailNotExistException {
        String currentUsername = principal.getName();

        userService.updateUser(userDto, currentUsername);

        ResponseDto responseDto = new ResponseDto("Update successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable("username") String username, Principal principal) throws DeleteException {
        String currentUsername = principal.getName();

        UserDto userDto = new UserDto();
        userDto.setUsername(username);

        userService.deleteUser(userDto, currentUsername);

        ResponseDto responseDto = new ResponseDto("Update successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/updateUserRole")
    public ResponseEntity<ResponseDto> updateUserRole(@Valid @RequestBody UserDto userDto, Principal principal)
            throws UsernameNotExistException, UpdateException, ConcurrentUpdateException, EmailNotExistException {
        String currentUsername = principal.getName();

        userService.updateUser(userDto, currentUsername);

        ResponseDto responseDto = new ResponseDto("Update successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null){
            ResponseDto responseDto = new ResponseDto("Login failed", HttpStatus.BAD_REQUEST, errorMap);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }
}
