package com.scm.backend.model.dto;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class SupplierDto extends SupperDto {
    @EqualsAndHashCode.Include
    @Digits(integer = 9, fraction = 0)
    private Integer supplierNumber;

    @Size(min = 1, max = 50)
    private String name;

    private String email;

    @Pattern(regexp="(^$|[0-9]{10})")
    @Size(min = 10, max = 11)
    private String phoneNumber;

    private String taxNumber;

    private Long type;

    private String address;

    private String province;

    private String ward;

    private String district;

    private String remark;

    // auto
    private Long paid = 0L;

    private LocalDate latestSupply;
}
