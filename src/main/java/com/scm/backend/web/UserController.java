package com.scm.backend.web;

import com.google.common.collect.Sets;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.dto.UserDto;
import com.scm.backend.model.entity.User;
import com.scm.backend.model.exception.UsernameAlreadyExistException;
import com.scm.backend.payload.JWTLoginSuccessResponse;
import com.scm.backend.payload.LoginRequest;
import com.scm.backend.security.JwtTokenProvider;
import com.scm.backend.service.MapValidationErrorService;
import com.scm.backend.service.UserService;
import com.scm.backend.util.UserDtoMapper;
import com.scm.backend.validator.UserDtoValidator;
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

import static com.scm.backend.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserDtoValidator userValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@Valid @RequestBody UserDto userDto, BindingResult result) throws UsernameAlreadyExistException {
        userValidator.validate(userDto, result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) {
            ResponseDto responseDto = new ResponseDto("Create failed", HttpStatus.BAD_REQUEST, errorMap);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        userService.saveUser(userDto);

        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.CREATED, null);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public UserDto getUserByUsername(@PathVariable("username") String username) throws UsernameNotFoundException{
        User user = userService.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Can not found username: " + username));
        return userDtoMapper.toUserDto(user);
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
