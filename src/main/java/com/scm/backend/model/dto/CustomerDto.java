package com.scm.backend.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CustomerDto extends SupperDto {
    @EqualsAndHashCode.Include
    @Digits(integer = 9, fraction = 0)
    private Integer customerNumber;

    @Size(min = 1, max = 50)
    private String name;

    private String email;

    @Size(min = 10, max = 11)
    private String phoneNumber;

    private Date dateOfBirth;

    private String taxNumber;

    private Long sex;

    private String address;

    private String province;

    private String ward;

    private String district;

    private String remark;

    //auto
    private Long paid = 0L;

    private LocalDate latestBuy;
}
