package com.scm.backend.validator;

import com.scm.backend.model.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        UserDto userDto = (UserDto) object;

        if(userDto.getPassword().length() < 6){
            errors.rejectValue("password","Length", "Password must be at least 6 characters");
        }

        if(StringUtils.isBlank(userDto.getConfirmPassword())){
            errors.rejectValue("confirmPassword","Blank", "Confirm password is required");
        } else {
            if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "Match", "Passwords must match");
            }
        }
    }
}
