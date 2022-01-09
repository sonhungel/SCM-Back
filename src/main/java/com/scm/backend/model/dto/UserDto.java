package com.scm.backend.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UserDto extends SupperDto {
    @EqualsAndHashCode.Include
    @NotBlank
    private String username;

    @NotBlank
    private String fullName;

    @NotBlank
    private String password;

    private String email;

    @Pattern(regexp="(^$|[0-9]{10})")
    @Size(min = 10, max = 11)
    private String phoneNumber;

    private Date dateOfBirth;

    private String address;

    private String province;

    private String ward;

    private String district;

    private String confirmPassword;

    private Date create_At;

    private Date update_At;

    private List<PermissionDto> permissionList;

    // first permission
    private String role;
}
