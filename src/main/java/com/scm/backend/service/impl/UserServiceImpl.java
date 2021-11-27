package com.scm.backend.service.impl;

import com.scm.backend.model.dto.UserDto;
import com.scm.backend.model.entity.User;
import com.scm.backend.model.exception.UsernameAlreadyExistException;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveUser (UserDto userDto) throws UsernameAlreadyExistException {
        checkBeforeCreateUser(userDto);

        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setConfirmPassword("");

        User user = createNewUser(userDto);

        //Username has to be unique (exception)

        // Make sure that password and confirmPassword match
        // We don't persist or show the confirmPassword
        userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    private void checkBeforeCreateUser(UserDto userDto) throws UsernameAlreadyExistException {
        if(userRepository.findUserByUsername(userDto.getUsername()).isPresent()){
            throw new UsernameAlreadyExistException("Username already exist", userDto.getUsername());
        }
    }

    private User createNewUser(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .fullName(userDto.getFullName())
                .password(userDto.getPassword())
                .confirmPassword(userDto.getConfirmPassword())
                .build();
    }
}
