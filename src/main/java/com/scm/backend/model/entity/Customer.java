package com.scm.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Customer extends SupperEntity {
    @Column(unique = true, nullable = false)
    private Integer customerNumber;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @Column
    private String email;

    @Column
    @Size(min = 10, max = 11)
    private String phoneNumber;

    @Column
    private Date dateOfBirth;

    @Column
    private String taxNumber;

    @Column
    private Long sex;

    @Column(columnDefinition = "nvarchar(255)")
    private String address;

    @Column
    private String province;

    @Column
    private String ward;

    @Column
    private String district;

    @Column(columnDefinition = "nvarchar(255)")
    private String remark;

    @Column
    private Long paid = 0L;

    @Column
    private LocalDate latestBuy;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Invoice> invoiceSet;
}
