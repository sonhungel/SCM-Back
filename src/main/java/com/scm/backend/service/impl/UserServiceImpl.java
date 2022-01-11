package com.scm.backend.service.impl;

import com.scm.backend.model.dto.UserDto;
import com.scm.backend.model.dto.UserRoleDto;
import com.scm.backend.model.entity.Role;
import com.scm.backend.model.entity.User;
import com.scm.backend.model.entity.UserRole;
import com.scm.backend.model.exception.*;
import com.scm.backend.repository.RoleRepository;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.repository.UserRoleRepository;
import com.scm.backend.service.HelperService;
import com.scm.backend.service.UserRoleService;
import com.scm.backend.service.UserService;
import com.scm.backend.util.InternalState;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private HelperService helperService;

    @Override
    public User saveUser(UserDto userDto) throws UsernameAlreadyExistException, EmailNotExistException, UpdateException {
        checkBeforeCreateUser(userDto);

        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setConfirmPassword("");

        User user = createNewUser(userDto);

        //Username has to be unique (exception)

        // Make sure that password and confirmPassword match
        // We don't persist or show the confirmPassword
        userRepository.saveAndFlush(user);
        createUserRoleInternal(user, userDto.getRole());
        return user;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UserDto userDto, String currentUsername) throws UsernameNotExistException, UpdateException, ConcurrentUpdateException, EmailNotExistException {
        User user = checkBeforeUpdate(userDto, currentUsername);

        updateUserWithNewData(user, userDto);

        return user;
    }

    @Override
    public void deleteUser(UserDto userDto, String currentUsername) throws DeleteException {
        User user = userRepository.findUserByUsername(currentUsername).orElseThrow(() ->
                new UsernameNotFoundException("Current username not found while delete user"));
        if(StringUtils.equals(user.getUserRoleList().get(0).getKey().getRole().getName(), "Quản lý")){
            User userBeDelete = userRepository.findUserByUsername(userDto.getUsername()).orElseThrow(() ->
                    new UsernameNotFoundException("Current username not found while delete user"));
            if(Objects.equals(user, userBeDelete)){
                throw new DeleteException("User can not delete itself");
            }
            userBeDelete.setInternalState(InternalState.DELETED);
            userRepository.saveAndFlush(userBeDelete);
        } else {
            throw new DeleteException("Only manager can delete user");
        }
    }

    private void updateUserWithNewData(User user, UserDto userDto) {

        if(StringUtils.isNotBlank(userDto.getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }

        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setAddress(userDto.getAddress());
        user.setProvince(userDto.getProvince());
        user.setWard(userDto.getWard());
        user.setDistrict(userDto.getDistrict());
        user.setUpdateDate(LocalDate.now());

        userRepository.save(user);
    }

    private User checkBeforeUpdate(UserDto userDto, String currentUsername) throws UsernameNotExistException, ConcurrentUpdateException, UpdateException, EmailNotExistException {
        User user = userRepository.findUserByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotExistException("Username not exist while update info", userDto.getUsername()));

        User currentUser = userRepository.findUserByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotExistException("Current username not exist while update info", currentUsername));
        if(!validateEmail(userDto.getEmail())){
            throw new EmailNotExistException("Email not exist", userDto.getEmail());
        }

        if(!Objects.equals(user.getVersion(), userDto.getVersion())){
            throw new ConcurrentUpdateException("Cannot update user, version have been changed.");
        }
        if(!currentUser.getUserRoleList().isEmpty()){
            if(!StringUtils.equals(currentUser.getUserRoleList().get(0).getKey().getRole().getName(), "Quản lý")) { // not manager
                if(!Objects.equals(user.getUsername(), currentUsername)){
                    throw new UpdateException("Update failed, only manager can update another user");
                }
            } else { // is manager
                if(!user.getUserRoleList().isEmpty()) {
                    if(!StringUtils.equals(user.getUserRoleList().get(0).getKey().getRole().getName(), userDto.getRole())) {
                        List<Role> roleList = roleRepository.findByName(userDto.getRole());

                        if(roleList.isEmpty()) {
                            throw new UpdateException("Role name not found when update role for user");
                        }

                        Role newRole = roleList.get(0);
                        Role oldRole = user.getUserRoleList().get(0).getKey().getRole();

                        if(!Objects.equals(newRole, oldRole)){
                            userRoleRepository.deleteByUserIdRoleId(user.getId());
                            userRoleService.createUserRoleByKeyId(user.getId(), newRole.getId());
                        }
                    }
                } else {
                    List<Role> roleList = roleRepository.findByName(userDto.getRole());
                    if(roleList.isEmpty()) {
                        throw new UpdateException("Role name not found when update role for user");
                    }
                    Role role = roleList.get(0);

                    userRoleService.createUserRoleByKeyId(user.getId(), role.getId());
                }
            }
        }

        return user;
    }

    private void checkBeforeCreateUser(UserDto userDto) throws UsernameAlreadyExistException, EmailNotExistException {
        if(!validateEmail(userDto.getEmail())){
            throw new EmailNotExistException("Email not exist", userDto.getEmail());
        }
        if(userRepository.findUserByUsername(userDto.getUsername()).isPresent()){
            throw new UsernameAlreadyExistException("Username already exist", userDto.getUsername());
        }
    }

    private boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private User createNewUser(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .fullName(userDto.getFullName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .dateOfBirth(userDto.getDateOfBirth())
                .address(userDto.getAddress())
                .province(userDto.getProvince())
                .district(userDto.getDistrict())
                .ward(userDto.getWard())
                .confirmPassword(userDto.getConfirmPassword())
                .addedDate(LocalDate.now())
                .build();
    }

    private void createUserRoleInternal(User user, String roleName) throws UpdateException {

        List<Role> roleList = roleRepository.findByName(roleName);
        if(roleList.isEmpty()) {
            throw new UpdateException("Role name not found when create role for user");
        }
        Role role = roleList.get(0);

        userRoleService.createUserRoleByKeyId(user.getId(), role.getId());
    }
}
