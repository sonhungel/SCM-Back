package com.scm.backend.service.impl;

import com.scm.backend.model.dto.UserDto;
import com.scm.backend.model.entity.User;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveUser (UserDto userDto){
        //todo: check before create, such as check username for not skip id in case create obj user and username exist
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        User user = createNewUser(userDto);

        //Username has to be unique (exception)

        // Make sure that password and confirmPassword match
        // We don't persist or show the confirmPassword
        userRepository.save(user);
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
